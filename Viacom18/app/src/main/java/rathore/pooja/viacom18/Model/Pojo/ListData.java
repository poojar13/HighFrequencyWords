package rathore.pooja.viacom18.Model.Pojo;

import java.util.Comparator;


public class ListData {

    public String wordName;
    public int wordCount;

    public ListData(String wordName, int wordCount) {
        this.wordCount = wordCount;
        this.wordName = wordName;
    }

    public ListData() {

    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public static class FrequencyComparator implements Comparator<ListData> {
        @Override
        public int compare(ListData o1, ListData o2) {
            {
                int wordCount1 = o1.getWordCount();
                int wordCount2 = o2.getWordCount();

                if (wordCount1 == wordCount2)
                    return 0;
                else if (wordCount1 > wordCount2)
                    return 1;
                else
                    return -1;
            }
        }
    }

}
