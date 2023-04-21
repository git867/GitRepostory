package Thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*需求：对字符串增加空格构建成句子，句子中所有的单词都在词典中，返回所有的句子

* 思路：1.定义两层循环，i,j
*      2.外层循环i依次遍历字符串所有字符,内层循环j依次遍历当前循环之前的所有字符。
*      3.截取[j,i]的字符串，判断是否在字典中存在，如果存在，保存到list
*      4.依次截取字符串，如果在字典中存在，保存到子集合list中
*      5.当i遍历结束， 拿到最后一个所有拼接好单词的子集合lists.get(length - 1)
*
* */

public class Main {


    public static void main(String[] args) {
        String [] testTable = {"catsanddog","applepenapple","catsandog"};
        //内层list集合存放每次循环的结果
        List<List<String>> testTable2=new ArrayList<List<String>>();
        List<String> list=new ArrayList<>();
        list.add("cat");
        list.add("cats");
        list.add("and");
        list.add("sand");
        list.add("dog");
        testTable2.add(list);
        list=new ArrayList<>();
        list.add("apple");
        list.add("pen");
        testTable2.add(list);
        list=new ArrayList<>();
        list.add("cats");
        list.add("dog");
        list.add("sand");
        list.add("and");
        list.add("cat");
        testTable2.add(list);

        for(int i=0;i<testTable.length;i++){
            //字符串，子集合字典List
            test(testTable[i], testTable2.get(i));
        }

    }

    private static void test(String ito,List<String> ito2) {
        Solution solution = new Solution();
        long begin = System.currentTimeMillis();
        System.out.print(ito);
        System.out.println();
        for(String str:ito2){
            System.out.print(str+" ");
        }
        System.out.println();
        //传入当前字符串和数组
        List<String> rtn = solution.wordBreak(ito,ito2);//执行程序
        long end = System.currentTimeMillis();

        System.out.println("所有返回的可能的句子=" );
        for(String str:rtn){
            System.out.println(str+" ");
        }
        System.out.println();
        System.out.println("耗时：" + (end - begin) + "ms");
        System.out.println("-------------------");
    }


}


class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {

        int length = s.length();//catsanddog的长度10
        if (length == 0) {
            return new ArrayList<String>();
        }
        Set<String> set = new HashSet<>(wordDict);// 备忘录去重之后的单词字典set = 【"cat","cats","and","sand","dog"】
        // list[i] 代表[0,i]的字符串，拆分后的字符串数组

        //定义一个大集合,用于遍历存放i之前的所有单词的list
        List<List<String>> lists = new ArrayList<>();

        //在一个lists大集合中创建了10个空的子集合,将每一个内层循环拆分的单词放到每一个子集合List中
        for (int i = 0; i < length; i++) {
            lists.add(new ArrayList<>());
        }




        /*每次循环截取，外层循环内嵌套内层循环[j,i]之间的所有字符串放到当前循环的list中
        * i从0到10,依次拼接
        */
        for (int i = 0; i < length; i++) {

            //当第i次循环的时候，将该次循环的所有截取字符串放到当前子集合list中
            List<String> list = lists.get(i);

            //外层每次循环，内层都从j=0开始，
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    //截取首字母到i的这部分字符串,[首字母,i]
                    String right = s.substring(j, i + 1);
                    if (set.contains(right)) {//备忘录字典中存在
                        list.add(right);//是一个标准单词，添加到子集合list中
                    }
                } else {

                    //截取到[j,i]的所有字符串
                    String right = s.substring(j, i + 1);
                    //如果在字典中包含
                    if (set.contains(right)) {//比如“sand”
                        //如果单词包含的话，拿上一个单词作为左边进行拼接,比如：cat sand,cats and
                        List<String> leftList = lists.get(j-1);
                        //拼接之后放到当前i的list中
                        for (String left : leftList) {
                            list.add(left + " " + right);
                        }
                    }
                }
            }
        }
        //拿到最后一个
        return lists.get(length - 1);
    }

}