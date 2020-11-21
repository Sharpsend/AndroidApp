package dev.goteam.sharpsend.utils;

import dev.goteam.sharpsend.R;

public class NetworkUtils {

    public static String getSimId(String simName) {
        if (simName.contains("mtn"))
            return Constants.MTN;
        else if (simName.contains("airtel") || simName.contains("zain") || simName.contains("econet"))
            return Constants.AIRTEL;
        else if (simName.contains("glo") || simName.contains("globacom"))
            return Constants.GLO;
        else if (simName.contains("etisalat") || simName.contains("9mobile") || simName.contains("mobile9"))
            return Constants.MOBILE_9;
        return null;
    }

    public static int getSimRes(String simId) {
        switch (simId) {
            case Constants.MTN:
                return R.drawable.mtn;
            case Constants.AIRTEL:
                return R.drawable.airtel;
            case Constants.GLO:
                return R.drawable.glo;
            case Constants.MOBILE_9:
                return R.drawable.mobile_9;
        }
        return 0;
    }

}
