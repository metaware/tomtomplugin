package com.metawarelabs.tomtomplugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import com.tomtom.navapp.Address;
import com.tomtom.navapp.Build;
import com.tomtom.navapp.GeoCoder;
import com.tomtom.navapp.LocationManager;
import com.tomtom.navapp.MapInfo;
import com.tomtom.navapp.NavAppClient;
import com.tomtom.navapp.Routeable;
import com.tomtom.navapp.Trip;
import com.tomtom.navapp.TripEvent;
import com.tomtom.navapp.TripEvent.ModifyResult;
import com.tomtom.navapp.TripEventManager;
import com.tomtom.navapp.TripManager;
import com.tomtom.navapp.Utils;
import com.tomtom.navapp.LocationManager.DisplayLocationListener;
import com.tomtom.navapp.LocationManager.DisplayLocationResult;
import com.tomtom.navapp.Trip.PlanResult;
import com.tomtom.navapp.Trip.RequestError;
import com.tomtom.navapp.Trip.SpeedLimitResult;
import com.tomtom.navapp.Trip.SpeedShieldShape;
import com.tomtom.navapp.Trip.SpeedShieldLuminance;
import com.tomtom.navapp.ErrorCallback;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.tomtom.navapp.NavAppError;

public class TomTomPlugin extends CordovaPlugin {
  public static final String ACTION_OPEN_NAV_APP_ENTRY = "openNavAppClient"; 
  private Trip mTrip = null; 
  private NavAppClient mNavappClient = null;
  private final static String TAG = "TomTomPlugin";
  private TripManager mTripManager = null;

  private ErrorCallback mErrorCallback = new ErrorCallback() {
    @Override
    public void onError(NavAppError error) {
      Log.e(TAG, "onError(" + error.getErrorMessage() + ")\n" + error.getStackTraceString());
    }
  };

  public boolean createNavAppClient() {
    if (mNavappClient == null) {
        // Create the NavAppClient
        try {
            mNavappClient = NavAppClient.Factory.make(this.cordova.getActivity(), mErrorCallback);
        } catch (RuntimeException e) {
            Log.e(TAG, "Failed creating NavAppClient", e);
            return false;
        }
    }
    return true;
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    createNavAppClient();
    
    try {
     if (ACTION_OPEN_NAV_APP_ENTRY.equals(action)) { 
        JSONObject arg_object = args.getJSONObject(0);
        mTripManager = getClient().getTripManager();
        final Routeable destination = getClient().makeRouteable(arg_object.getDouble("latitude"), arg_object.getDouble("longitude"));
        mTripManager.planTrip(destination, mPlanListener);
        callbackContext.success();
        return true;
      }
      callbackContext.error("Invalid action");
      return false;
    } catch(Exception e) {
        System.err.println("Exception: " + e.getMessage());
        callbackContext.error(e.getMessage());
        return false;
    }
  }

  public NavAppClient getClient() {
    return mNavappClient;
  }

  private Trip.PlanListener mPlanListener = new Trip.PlanListener() {
    public void onTripPlanResult(Trip trip, PlanResult result) {
      Log.d(TAG, "onTripPlanResult result["+result+"]");
      // save the created trip
      mTrip = trip;
    }
  };


}