package de.ba.tiagosenc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dkpro.lab.reporting.BatchReportBase;
import org.dkpro.lab.reporting.FlexTable;
import org.dkpro.lab.storage.StorageService;
import org.dkpro.lab.task.TaskContextMetadata;
import org.dkpro.tc.core.Constants;
import org.dkpro.tc.core.util.ReportUtils;
import org.dkpro.tc.ml.report.TcTaskTypeUtil;
import org.dkpro.tc.util.EvaluationReportUtil;

public class BatchOwnCVReport
	extends BatchReportBase
	implements Constants
{
	
	boolean softEvaluation = true;
	boolean individualLabelMeasures = true;
	
	@Override
	public void execute()
	    throws Exception
	{
	
	    StorageService store = getContext().getStorageService();
	
	    FlexTable<String> table = FlexTable.forClass(String.class);
	
	    for (TaskContextMetadata subcontext : getSubtasks()) {
	        if (!TcTaskTypeUtil.isCrossValidationTask(store, subcontext.getId())) {
	            continue;
	        }
	        Map<String, String> discriminatorsMap = ReportUtils.getDiscriminatorsForContext(store,
	                subcontext.getId(), Constants.DISCRIMINATORS_KEY_TEMP);
	
	        File fileToEvaluate = store.locateKey(subcontext.getId(),
	                Constants.TEST_TASK_OUTPUT_KEY + "/" + Constants.SERIALIZED_ID_OUTCOME_KEY);
	
	        Map<String, String> resultMap = EvaluationReportUtil.getResultsHarmonizedId2Outcome(
	                fileToEvaluate, softEvaluation, individualLabelMeasures);
	
	        Map<String, String> values = new HashMap<String, String>();
	        values.putAll(discriminatorsMap);
	        values.putAll(resultMap);
	
	        table.addRow(subcontext.getLabel(), values);
	    }
	
	    /*
	     * TODO: make rows to columns e.g. create a new table and set columns to rows of old table
	     * and rows to columns but than must be class FlexTable in this case adapted accordingly:
	     * enable setting
	     */
	
	    ReportUtils.writeExcelAndCSV(getContext(), getContextLabel(), table, EVAL_FILE_NAME, SUFFIX_EXCEL, SUFFIX_CSV);
	}
}
