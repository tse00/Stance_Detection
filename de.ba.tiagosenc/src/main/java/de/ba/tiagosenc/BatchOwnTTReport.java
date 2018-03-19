package de.ba.tiagosenc;

import static org.dkpro.tc.core.util.ReportUtils.getDiscriminatorValue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.dkpro.lab.reporting.BatchReportBase;
import org.dkpro.lab.reporting.FlexTable;
import org.dkpro.lab.storage.StorageService;
import org.dkpro.lab.storage.impl.PropertiesAdapter;
import org.dkpro.lab.task.Task;
import org.dkpro.lab.task.TaskContextMetadata;
import org.dkpro.tc.core.Constants;
import org.dkpro.tc.core.util.ReportUtils;
import org.dkpro.tc.ml.report.TcTaskTypeUtil;
import org.dkpro.tc.util.EvaluationReportUtil;

public class BatchOwnTTReport
    extends BatchReportBase
    implements Constants
{
    private final List<String> discriminatorsToExclude = Arrays
            .asList(new String[] { DIM_FILES_VALIDATION, DIM_FILES_TRAINING });
    private boolean softEvaluation = true;
    private boolean individualLabelMeasures = true;

    @Override
    public void execute()
        throws Exception
    {
        StorageService store = getContext().getStorageService();
        FlexTable<String> table = FlexTable.forClass(String.class);

        for (TaskContextMetadata subcontext : getSubtasks()) {

            if (!TcTaskTypeUtil.isMachineLearningAdapterTask(store,
                    subcontext.getId())) {
                continue;
            }
            Map<String, String> discriminatorsMap = getDiscriminators(store, subcontext.getId());
            discriminatorsMap = ReportUtils.clearDiscriminatorsByExcludePattern(discriminatorsMap,
                    discriminatorsToExclude);

            // add the results into the discriminator map
            File id2o = getContext().getStorageService().locateKey(subcontext.getId(),
                    ID_OUTCOME_KEY);
            String mode = getDiscriminatorValue(discriminatorsMap, DIM_LEARNING_MODE);
            Map<String, String> resultMap = EvaluationReportUtil.getResultsId2Outcome(id2o, mode,
                    softEvaluation, individualLabelMeasures);
            discriminatorsMap.putAll(resultMap);

            table.addRow(subcontext.getLabel(), discriminatorsMap);
        }

        ReportUtils.writeExcelAndCSV(

        getContext(), getContextLabel(), table, EVAL_FILE_NAME, SUFFIX_EXCEL, SUFFIX_CSV);
    }

    private Map<String, String> getDiscriminators(StorageService store, String id)
    {
        return store.retrieveBinary(id, Task.DISCRIMINATORS_KEY, new PropertiesAdapter()).getMap();
    }

}
