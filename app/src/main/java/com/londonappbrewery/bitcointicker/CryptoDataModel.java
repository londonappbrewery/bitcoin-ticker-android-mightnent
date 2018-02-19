package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunne on 18/2/2018.
 */

public class CryptoDataModel {
    private String bidPrice;
    private String askPrice;
    public static CryptoDataModel fromJson(JSONObject jsonObject){
        try{
            CryptoDataModel coinPx = new CryptoDataModel();
            double biddingPx = jsonObject.getDouble("bid");
            coinPx.bidPrice = Double.toString(biddingPx);
            double askingPx = jsonObject.getDouble("ask");
            coinPx.askPrice = Double.toString(askingPx);

            return coinPx;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public String getAskPrice() {
        return askPrice;
    }
}


