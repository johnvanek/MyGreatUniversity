package com.example.android.mygreatuniversity.Utils;

import com.example.android.mygreatuniversity.Entity.Course;

import java.util.List;

public class StateManager {
    public static final class SelectedTermState {
        //Constructor
        private SelectedTermState() {
        }
        //Fields
        private static int termID;
        private static String termTitle, termStart, termEnd;
        private static List<Course> termCourses;

        public static int getTermID() {
            return termID;
        }

        public static void setTermID(int termID) {
            SelectedTermState.termID = termID;
        }

        public static String getTermTitle() {
            return termTitle;
        }

        public static void setTermTitle(String termTitle) {
            SelectedTermState.termTitle = termTitle;
        }

        public static String getTermStart() {
            return termStart;
        }

        public static void setTermStart(String termStart) {
            SelectedTermState.termStart = termStart;
        }

        public static String getTermEnd() {
            return termEnd;
        }

        public static void setTermEnd(String termEnd) {
            SelectedTermState.termEnd = termEnd;
        }

        public static List<Course> getTermCourses() {
            return termCourses;
        }

        public static void setTermCourses(List<Course> termCourses) {
            SelectedTermState.termCourses = termCourses;
        }
    }
}
