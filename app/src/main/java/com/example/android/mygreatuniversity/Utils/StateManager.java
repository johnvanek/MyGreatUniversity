package com.example.android.mygreatuniversity.Utils;

import android.app.Application;

import com.example.android.mygreatuniversity.Database.Repo;

public class StateManager {
    static Application app;

    public static int loggedInUserID = 0;
    public static void setApp(Application application) {
        app = application;
    }
    public static Application getApp(){
        return app;
    }
    public static final class SelectedTerm {


        //Constructor
        private SelectedTerm () {
            // Do nada.. effectively final? Supposed to be a global static class.
        }
        //Fields
        private static boolean termSelected;
        private static int termID;

        public static boolean isTermSelected() {
            return termSelected;
        }

        public static void setIsTermSelected(boolean isTermSelected) {
            SelectedTerm.termSelected = isTermSelected;
        }

        public static int getTermID() {
            return termID;
        }

        public static void setTermID(int termID) {
            SelectedTerm.termID = termID;
        }
    }

    private static boolean arrivedToCourseFromTermView;

    public static boolean isArrivedToCourseFromTermView() {
        return arrivedToCourseFromTermView;
    }

    public static void setArrivedToCourseFromTermView(boolean arrivedToCourseFromTermView) {
        StateManager.arrivedToCourseFromTermView = arrivedToCourseFromTermView;
    }
}
