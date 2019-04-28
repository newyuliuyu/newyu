package com.newyu.fx.spi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.exam.Item;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.exam.Subject;
import com.newyu.domain.exam.SubjectCj;
import com.newyu.domain.fx.ScoreInfo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ClassName: FxStatistical <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 上午10:59 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class FxStatistical {

    private Subject subject;
    private List<StudentCj> studentCjs;
    private List<ScoreInfo> scoreInfos;
    private int totalNum = 0;


    private void init() {
        totalNum = studentCjs.size();
        studentCjs.sort((v1, v2) -> {
            double score1 = v1.getSubjectCj(subject).getScore();
            double score2 = v2.getSubjectCj(subject).getScore();
            return Double.compare(score2, score1);
        });
    }

    public void createScoreInfo(Function<SubjectCj, Double> mapper) {
        Map<Double, Integer> scoreNumMap = studentCjs.stream()
                .map(x -> mapper.apply(x.getSubjectCj(subject)))
                .collect(Collectors.groupingBy(x -> x, Collectors.reducing(0, x -> 1, Integer::sum)));
        scoreInfos = createScoreInfo(scoreNumMap);
    }

    private List<ScoreInfo> createScoreInfo(Map<Double, Integer> scoreNumMap) {

        List<ScoreInfo> scoreInfos = scoreNumMap.entrySet().stream().map(x -> ScoreInfo.builder()
                .score(x.getKey())
                .num(x.getValue())
                .build()).collect(Collectors.toList());

        scoreInfos.sort((v1, v2) -> {
            return Double.compare(v2.getScore(), v1.getScore());
        });

        int rank = 1;
        for (ScoreInfo scoreInfo : scoreInfos) {
            scoreInfo.setRank(rank);
            rank += scoreInfo.getNum();
        }
        ListIterator<ScoreInfo> listIterator = scoreInfos.listIterator(scoreInfos.size());
        rank = 1;
        while (listIterator.hasPrevious()) {
            ScoreInfo scoreInfo = listIterator.previous();
            scoreInfo.setBackRank(rank);
            rank += scoreInfo.getNum();
        }

        return scoreInfos;
    }


    public static FxStatistical newInstance(Subject subject, List<StudentCj> studentCjs) {
        FxStatistical fxStatistical = new FxStatistical();
        fxStatistical.subject = subject;
        fxStatistical.studentCjs = studentCjs;
        fxStatistical.init();
        return fxStatistical;
    }

    public int getCount() {
        return totalNum;
    }


    /**
     * 平均分
     *
     * @return
     */
    public double getAvg() {

        double sumScore = scoreInfos.stream().reduce(0d, (v1, v2) -> v1 + v2.getScore() * v2.getNum(), (v1, v2) -> v1 + v2);
        double result = sumScore / getCount();
        return Double.isNaN(result) ? 0 : result;
    }

    /**
     * 方差
     *
     * @return
     */
    public double getVariance() {
        double result = 0d;
        double avg = getAvg();
        double variance = scoreInfos.stream().reduce(0d, (v1, v2) -> v1 + Math.pow(v2.getScore() - avg, 2) * v2.getNum(), (v1, v2) -> v1 + v2);
        result = variance / (getCount() - 1);
        return Double.isNaN(result) ? 0 : result;
    }

    /**
     * 标准差
     *
     * @return
     */
    public double getStd() {
        double variance = getVariance();
        return Math.sqrt(variance);
    }

    /**
     * 最高分
     *
     * @return
     */
    public double getMax() {
        double score = 0d;
        if (scoreInfos.size() > 0) {
            score = scoreInfos.get(0).getScore();
        }
        return score;
    }

    /**
     * 最低分
     *
     * @return
     */
    public double getMin() {
        double score = 0d;
        if (scoreInfos.size() > 0) {
            score = scoreInfos.get(scoreInfos.size() - 1).getScore();
        }
        return score;
    }

    /**
     * 众数
     *
     * @return
     */
    public String getMode() {
        DecimalFormat format = new DecimalFormat("0.##");
        format.setRoundingMode(RoundingMode.HALF_UP);

        List<String> data = Lists.newArrayList();
        int maxNum = 0;
        for (ScoreInfo si : scoreInfos) {
            int personNum = si.getNum();
            if (personNum == maxNum) {
                data.add(format.format(si.getScore()));
            } else if (personNum > maxNum) {
                maxNum = personNum;
                data = Lists.newArrayList();
                data.add(format.format(si.getScore()));
            }
        }


        return String.join(",", data.stream().limit(5).collect(Collectors.toList()));
    }

    /**
     * 获取大于等于score的人数
     *
     * @param score
     * @return
     */
    public int getGEScoreNumberOfPeople(double score) {
        int num = 0;
        for (ScoreInfo scoreinfo : scoreInfos) {
            if (Double.compare(scoreinfo.getScore(), score) >= 0) {
                num += scoreinfo.getNum();
            } else {
                break;
            }
        }
        return num;
    }

    /**
     * 获取等于score的人数
     *
     * @param score
     * @return
     */
    public int getLEqualScoreNumberOfPeople(double score) {
        int num = 0;
        ListIterator<ScoreInfo> listIterator = scoreInfos.listIterator(scoreInfos.size());
        while (listIterator.hasPrevious()) {
            ScoreInfo scoreinfo = listIterator.previous();
            if (Double.compare(score, scoreinfo.getScore()) >= 0) {
                num += scoreinfo.getNum();
            } else {
                break;
            }
        }
        return num;
    }

    /**
     * 获取百分等级位置的分数
     *
     * @param percent
     * @return
     */
    public double getPercentPostionOfScore(double percent) {
        double score = 0d;
        for (ScoreInfo scoreinfo : scoreInfos) {
            double myPercent = scoreinfo.getRank() * 1.0 / getCount();
            if (Double.compare(percent, myPercent) >= 0) {
                score = scoreinfo.getScore();
            } else {
                break;
            }
        }
        return score;
    }

    /**
     * 获取小于score的人数
     *
     * @param score
     * @return
     */
    public int getLessScoreNumberOfPeople(double score) {
        int num = 0;
        ListIterator<ScoreInfo> listIterator = scoreInfos.listIterator(scoreInfos.size());
        while (listIterator.hasPrevious()) {
            ScoreInfo scoreinfo = listIterator.previous();
            if (Double.compare(score, scoreinfo.getScore()) > 0) {
                num += scoreinfo.getNum();
            } else {
                break;
            }
        }
        return num;
    }

    /**
     * 信度
     *
     * @return
     */
    public double getReliability() {
        Map<Item, Map<Double, Integer>> itemScoreNumMap = Maps.newHashMap();
        Map<Item, Double> itemSumScoreMap = Maps.newHashMap();

        List<Item> items = subject.getItems();
        for (StudentCj studentCj : studentCjs) {
            SubjectCj subjectCj = studentCj.getSubjectCj(subject);
            for (Item item : items) {
                double score = subjectCj.queryItemCj(item.getName()).getScore();
                Map<Double, Integer> scoreNumMap = itemScoreNumMap.get(item);
                if (scoreNumMap == null) {
                    scoreNumMap = Maps.newHashMap();
                    itemScoreNumMap.put(item, scoreNumMap);
                }
                Integer count = scoreNumMap.get(score);
                count = count == null ? 0 : count;
                scoreNumMap.put(score, count + 1);

                Double sumScore = itemSumScoreMap.get(item);
                sumScore = sumScore == null ? 0 : sumScore;
                itemSumScoreMap.put(item, sumScore + score);
            }
        }
        double itemVarianceSum = 0d;
        int size = getCount();
        for (Item item : items) {
            double sumScore = itemSumScoreMap.get(item);
            double avg = sumScore / size;
            Map<Double, Integer> scoreNumMap = itemScoreNumMap.get(item);
            double variance = scoreNumMap.entrySet().stream()
                    .reduce(0d, (x, y) -> x + Math.pow(y.getKey() - avg, 2) * y.getValue(), (x, y) -> x + y);
            itemVarianceSum += variance / (size - 1);
        }
        double testpaperVariance = getVariance();
        double itemSize = items.size();
        double reliability = (itemSize / (itemSize - 1)) * (1 - (itemVarianceSum / testpaperVariance));
        reliability = Double.isNaN(reliability) ? 0 : reliability;
        reliability = Double.isInfinite(reliability) ? 0 : reliability;
        return reliability;
    }

    /**
     * 区分度
     *
     * @return
     */
    public double getDiscrimination(Function<SubjectCj, Double> getScore, double fullScore) {
        DiscriminationCalclutor dc = new DiscriminationCalclutor(getScore, fullScore);
        return dc.calcluate();
    }


    class DiscriminationCalclutor {

        private Function<SubjectCj, Double> getScore;
        private double fullScore;

        public DiscriminationCalclutor(Function<SubjectCj, Double> getScore, double fullScore) {
            this.getScore = getScore;
            this.fullScore = fullScore;
        }

        public double calcluate() {
            double highAvg = calcluateHighAvgScore();
            double lowAvg = calcluateLowAvgScore();
            double result = (highAvg - lowAvg) / fullScore;
            result = Double.isNaN(result) ? 0 : result;
            result = Double.isInfinite(result) ? 0 : result;
            return result;
        }

        private Double calcluateHighAvgScore() {
            double sumScore = 0;
            int count = 0;
            double preScore = 0d;
            for (StudentCj studentCj : studentCjs) {
                SubjectCj subjectCj = studentCj.getSubjectCj(subject);
                double score = getScore.apply(subjectCj);
                count++;
                double percent = count / getCount();
                if (Double.compare(percent, 0.27) > 0
                        && Double.compare(preScore, score) == 0) {
                    break;
                }
                preScore = score;
                sumScore += score;
            }

            double avg = sumScore / count;
            avg = Double.isNaN(avg) ? 0 : avg;
            return avg;
        }

        private Double calcluateLowAvgScore() {
            double sumScore = 0;
            int count = 0;
            double preScore = 0d;
            ListIterator<StudentCj> listIterator = studentCjs.listIterator(studentCjs.size());
            while (listIterator.hasPrevious()) {
                StudentCj studentCj = listIterator.previous();
                SubjectCj subjectCj = studentCj.getSubjectCj(subject);
                double score = getScore.apply(subjectCj);
                count++;
                double percent = count / getCount();
                if (Double.compare(percent, 0.27) > 0
                        && Double.compare(preScore, score) == 0) {
                    break;
                }
                preScore = score;
                sumScore += score;
            }
            double avg = sumScore / count;
            avg = Double.isNaN(avg) ? 0 : avg;
            return avg;
        }
    }

}
