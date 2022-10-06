package com.example.android.mygreatuniversity.Utils;

import com.example.android.mygreatuniversity.Entity.Course;

import java.util.List;

//TODO don't need this class instead learn how to use the onSaveInstanceStateGetsCalled when navigating
// Away from an activity

public class StateManager {
    public static final class SelectedTerm {
        //Constructor
        private SelectedTerm () {
            // Do nada.. effectively final? Supposed to be a global static class.
        }
        //Fields
        private static boolean hasSavedData = false;
        private static boolean arrivedToCourseFromTermView = false;
        private static int termID;
        private static String termTitle, termStart, termEnd;
        private static List<Course> termCourses;

        public static void setHasSavedData(boolean hasSavedData) {
            SelectedTerm.hasSavedData = hasSavedData;
        }

        public static boolean getHasSavedData() {
            return hasSavedData;
        }

        public static int getTermID() {
            return termID;
        }

        public static void setTermID(int termID) {
            SelectedTerm.termID = termID;
        }

        public static String getTermTitle() {
            return termTitle;
        }

        public static void setTermTitle(String termTitle) {
            SelectedTerm.termTitle = termTitle;
        }

        public static String getTermStart() {
            return termStart;
        }

        public static void setTermStart(String termStart) {
            SelectedTerm.termStart = termStart;
        }

        public static String getTermEnd() {
            return termEnd;
        }

        public static void setTermEnd(String termEnd) {
            SelectedTerm.termEnd = termEnd;
        }

        public static List<Course> getTermCourses() {
            return termCourses;
        }

        public static void setTermCourses(List<Course> termCourses) {
            SelectedTerm.termCourses = termCourses;
        }

        public static boolean getArrivedToCourseFromTermView() {
            return arrivedToCourseFromTermView;
        }

        public static void setArrivedToCourseFromTermView(boolean arrivedToCourseFromTermView) {
            SelectedTerm.arrivedToCourseFromTermView = arrivedToCourseFromTermView;
        }
    }
}
