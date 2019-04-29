package com.newyu.fx.spi;

import com.google.common.collect.Lists;
import com.newyu.domain.exam.*;
import com.newyu.domain.fx.ScoreInfo;
import com.newyu.domain.fx.SubjectDataVersion;
import com.newyu.fx.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ClassName: Calculators <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 下午4:06 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class Calculators {

    private FxContext context;
    private Dataset<StudentCj> dataset;
    private List<FxData> groupFxData;

    private List<List<FxData>> zfFxDatas = Lists.newArrayList();
    private List<List<FxData>> segmentFxDatas = Lists.newArrayList();
    private List<List<FxData>> itemFxDatas = Lists.newArrayList();
    private List<List<FxData>> itemGroupFxDatas = Lists.newArrayList();


    public Calculators(FxContext context, Dataset<StudentCj> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    public void calculator() {
        baseInfo();
        List<StudentCj> studentCjs = dataset.getList();
        int bkrs = studentCjs.size();
        studentCjs = studentCjs.stream().filter(context.getPredicateOfStudent()).collect(Collectors.toList());
        List<Subject> subjects = context.getExamBaseInfoMgr().getSubjects();
        for (Subject subject : subjects) {
            SubjectDataVersion dataVersion = dataversion(subject);
            if (dataVersion.isCalculate()) {
                List<StudentCj> subjectStudentCjs = studentCjs.stream().filter(context.getPredicateOfSubjectCj(subject)).collect(Collectors.toList());
                if (!subjectStudentCjs.isEmpty()) {
                    FxStatistical fxStat = FxStatistical.newInstance(subject, subjectStudentCjs);
                    calcluateZF(subject, dataVersion, fxStat, bkrs);
                    calcluateSegment(subject, dataVersion, fxStat, bkrs);
                    calcluateKgZF(subject, dataVersion, fxStat, bkrs);
                    calcluateZgZF(subject, dataVersion, fxStat, bkrs);
                    calcluateItem(subject, dataVersion, fxStat, bkrs);
                    calcluateItemGroup(subject, dataVersion, "knowledge", fxStat, bkrs);
                    calcluateItemGroup(subject, dataVersion, "ability", fxStat, bkrs);
                    calcluateItemGroup(subject, dataVersion, "titleType", fxStat, bkrs);
                    calcluateItemGroup(subject, dataVersion, "bigTitleName", fxStat, bkrs);
                    calcluateItemGroup(subject, dataVersion, "smallTitleName", fxStat, bkrs);
                }
            }
        }
    }

    private void baseInfo() {
        groupFxData = Lists.newArrayList();
        groupFxData.add(FxData.builder().name("examId").value(context.getExamBaseInfoMgr().getExam().getId()).build());
        groupFxData.addAll(getGroupValue());
    }

    private List<FxData> getGroupValue() {
        if (dataset instanceof GroupDataset) {
            GroupValue groupValue = ((GroupDataset) dataset).getGroupValue();
            return groupValue.fetchFxData();
        }
        return Lists.newArrayList();
    }

    private SubjectDataVersion dataversion(Subject subject) {
        List<SubjectDataVersion> subjectDataVersions = context.getExamBaseInfoMgr().getSubjectDataVersions();
        Optional<SubjectDataVersion> subjectDataVersionOptional = subjectDataVersions.stream().filter(x -> x.getSubjectId().equals(subject.getId())).findFirst();
        return subjectDataVersionOptional.orElse(SubjectDataVersion.builder().subjectId(subject.getId())
                .curVesrion(1).previousVesrion(0).build());
    }


    private void baseFxData(Subject subject, SubjectDataVersion dataVersion, List<FxData> fxDatas, FxStatistical fxStat, double fullScore, int bkrs) {
        fxDatas.addAll(fxDatas);
        fxDatas.add(FxData.builder().name("subjectId").value(subject.getId()).build());
        fxDatas.add(FxData.builder().name("subjectName").value(subject.getName()).build());
        fxDatas.add(FxData.builder().name("dataVersion").value(dataVersion.getCurVesrion()).build());
        fxDatas.add(FxData.builder().name("fullScore").value(fullScore).build());
        fxDatas.add(FxData.builder().name("bkrs").value(bkrs).build());
        fxDatas.add(FxData.builder().name("skrs").value(fxStat.getCount()).build());
        fxDatas.add(FxData.builder().name("avgs").value(fxStat.getAvg()).build());
        fxDatas.add(FxData.builder().name("stds").value(fxStat.getStd()).build());
        fxDatas.add(FxData.builder().name("maxs").value(fxStat.getMax()).build());
        fxDatas.add(FxData.builder().name("mins").value(fxStat.getMin()).build());
        fxDatas.add(FxData.builder().name("zeroScoreNum").value(fxStat.getLEqualScoreNumberOfPeople(0)).build());
        fxDatas.add(FxData.builder().name("fullScoreNum").value(fxStat.getGEScoreNumberOfPeople(fullScore)).build());
    }

    private void baseFxDataZF(List<FxData> fxDatas, FxStatistical fxStat) {
        fxDatas.add(FxData.builder().name("modes").value(fxStat.getMode()).build());
        fxDatas.add(FxData.builder().name("q1").value(fxStat.getPercentPostionOfScore(0.25)).build());
        fxDatas.add(FxData.builder().name("q2").value(fxStat.getPercentPostionOfScore(0.5)).build());
        fxDatas.add(FxData.builder().name("q3").value(fxStat.getPercentPostionOfScore(0.75)).build());
    }

    private void calcluateZF(Subject subject, SubjectDataVersion dataVersion, FxStatistical fxStat, int bkrs) {
        List<FxData> fxDatas = Lists.newArrayList();
        fxStat.createScoreInfo(x -> x.getScore());
        baseFxData(subject, dataVersion, fxDatas, fxStat, subject.getFullScore(), bkrs);
        baseFxDataZF(fxDatas, fxStat);
        fxDatas.add(FxData.builder().name("discrimination").value(fxStat.getDiscrimination(x -> x.getScore(), subject.getFullScore())).build());
        fxDatas.add(FxData.builder().name("reliability").value(fxStat.getReliability()).build());
        fxDatas.add(FxData.builder().name("scoreType").value(1).build());

        zfFxDatas.add(fxDatas);
    }

    private void calcluateSegment(Subject subject, SubjectDataVersion dataVersion, FxStatistical fxStat, int bkrs) {
        for (ScoreInfo scoreInfo : fxStat.getScoreInfo()) {
            List<FxData> fxDatas = Lists.newArrayList();
            segmentFxDatas.add(fxDatas);
            fxDatas.addAll(groupFxData);
            fxDatas.add(FxData.builder().name("subjectId").value(subject.getId()).build());
            fxDatas.add(FxData.builder().name("subjectName").value(subject.getName()).build());
            fxDatas.add(FxData.builder().name("dataVersion").value(dataVersion.getCurVesrion()).build());
            fxDatas.add(FxData.builder().name("score").value(scoreInfo.getScore()).build());
            fxDatas.add(FxData.builder().name("num").value(scoreInfo.getNum()).build());
            fxDatas.add(FxData.builder().name("rank").value(scoreInfo.getRank()).build());
            fxDatas.add(FxData.builder().name("backRank").value(scoreInfo.getBackRank()).build());
        }

    }


    private void calcluateKgZF(Subject subject, SubjectDataVersion dataVersion, FxStatistical fxStat, int bkrs) {
        List<FxData> fxDatas = Lists.newArrayList();
        fxStat.createScoreInfo(x -> x.getKgScore());

        baseFxData(subject, dataVersion, fxDatas, fxStat, subject.getKgFullScore(), bkrs);
        baseFxDataZF(fxDatas, fxStat);
        fxDatas.add(FxData.builder().name("discrimination").value(fxStat.getDiscrimination(x -> x.getKgScore(), subject.getKgFullScore())).build());
        fxDatas.add(FxData.builder().name("reliability").value(0).build());
        fxDatas.add(FxData.builder().name("scoreType").value(2).build());

        zfFxDatas.add(fxDatas);
    }

    private void calcluateZgZF(Subject subject, SubjectDataVersion dataVersion, FxStatistical fxStat, int bkrs) {
        List<FxData> fxDatas = Lists.newArrayList();
        fxStat.createScoreInfo(x -> x.getZgScore());

        baseFxData(subject, dataVersion, fxDatas, fxStat, subject.getZgFullScore(), bkrs);
        baseFxDataZF(fxDatas, fxStat);

        fxDatas.add(FxData.builder().name("discrimination").value(fxStat.getDiscrimination(x -> x.getZgScore(), subject.getZgFullScore())).build());
        fxDatas.add(FxData.builder().name("reliability").value(0).build());
        fxDatas.add(FxData.builder().name("scoreType").value(3).build());
        zfFxDatas.add(fxDatas);
    }

    private void calcluateItem(Subject subject, SubjectDataVersion dataVersion, FxStatistical fxStat, int bkrs) {
        List<Item> items = subject.getItems();
        for (Item item : items) {
            List<FxData> fxDatas = Lists.newArrayList();
            itemFxDatas.add(fxDatas);
            if (item.isChoice()) {
                fxStat.createScoreInfo(x -> x.queryItemCj(item.getName()).getScore(), x -> x.queryItemCj(item.getName()).isChoiced());
            } else {
                fxStat.createScoreInfo(x -> x.queryItemCj(item.getName()).getScore());
            }
            baseFxData(subject, dataVersion, fxDatas, fxStat, item.getScore(), bkrs);
            fxDatas.add(FxData.builder().name("discrimination").value(fxStat.getDiscrimination(x -> x.queryItemCj(item.getName()).getScore(), item.getScore())).build());
            String selectStat = "";
            if (!item.getItemType().equals(ItemType.Not_Select)) {
                selectStat = fxStat.getItemSelectStat(item.getName());
            }
            fxDatas.add(FxData.builder().name("selectStat").value(selectStat).build());

            fxDatas.add(FxData.builder().name("itemName").value(item.getName()).build());
            fxDatas.add(FxData.builder().name("displayOrder").value(item.getDisplayOrder()).build());
            fxDatas.add(FxData.builder().name("itemId").value(item.getId()).build());
        }

    }

    private void calcluateItemGroup(Subject subject, SubjectDataVersion dataVersion, String itemProperty, FxStatistical fxStat, int bkrs) {
        List<ItemGroup> itemGroups = subject.getItemGroups(itemProperty);
        for (ItemGroup itemGroup : itemGroups) {
            List<FxData> fxDatas = Lists.newArrayList();
            itemGroupFxDatas.add(fxDatas);
            fxStat.createScoreInfo(x -> x.getItemGroupScore(itemGroup));
            baseFxData(subject, dataVersion, fxDatas, fxStat, itemGroup.getFullScore(), bkrs);
            fxDatas.add(FxData.builder().name("discrimination").value(fxStat.getDiscrimination(x -> x.getItemGroupScore(itemGroup), itemGroup.getFullScore())).build());
            fxDatas.add(FxData.builder().name("itemGroupName").value(itemGroup.getName()).build());
            fxDatas.add(FxData.builder().name("itemProperty").value(itemGroup.getItems().size()).build());
            fxDatas.add(FxData.builder().name("itemNum").value(itemGroup.getItemNames()).build());
        }

    }

}
