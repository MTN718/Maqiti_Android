package com.songu.maqiti.view.autocomplete.rest.responses;

import com.google.gson.annotations.SerializedName;
import com.songu.maqiti.view.autocomplete.rest.model.Prediction;


import java.util.ArrayList;

/**
 * Created by DAVID-WORK on 19/07/2015.
 */

public class PredictionResponse extends BaseResponse
{
    @SerializedName("predictions")
    private ArrayList<Prediction> mPredictionList;

    public ArrayList<Prediction> getPredictionList()
    {
        return mPredictionList;
    }

    @Override
    public String toString()
    {
        return "PredictionResponse{" +
                "mPredictionList=" + mPredictionList +
                "} " + super.toString();
    }
}
