package com.android.okhttptest.bean;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-12   15:37
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WeatherBean{

    /**
     * Radar :
     * SD : 79%
     * WD : 北风
     * WS : 2级
     * WSE : 2
     * city : 重庆
     * cityid : 101040100
     * isRadar : 1
     * njd :
     * qy : 0
     * temp : 13
     * time : 2017-03-12T14:39:24.57
     */

    private WeatherinfoEntity weatherinfo;

    public void setWeatherinfo(WeatherinfoEntity weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public WeatherinfoEntity getWeatherinfo() {
        return weatherinfo;
    }

    public static class WeatherinfoEntity {
        private String Radar;
        private String SD;
        private String WD;
        private String WS;
        private int WSE;
        private String city;
        private String cityid;
        private int isRadar;
        private String njd;
        private String qy;
        private String temp;
        private String time;

        public void setRadar(String Radar) {
            this.Radar = Radar;
        }

        public void setSD(String SD) {
            this.SD = SD;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public void setWSE(int WSE) {
            this.WSE = WSE;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public void setIsRadar(int isRadar) {
            this.isRadar = isRadar;
        }

        public void setNjd(String njd) {
            this.njd = njd;
        }

        public void setQy(String qy) {
            this.qy = qy;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRadar() {
            return Radar;
        }

        public String getSD() {
            return SD;
        }

        public String getWD() {
            return WD;
        }

        public String getWS() {
            return WS;
        }

        public int getWSE() {
            return WSE;
        }

        public String getCity() {
            return city;
        }

        public String getCityid() {
            return cityid;
        }

        public int getIsRadar() {
            return isRadar;
        }

        public String getNjd() {
            return njd;
        }

        public String getQy() {
            return qy;
        }

        public String getTemp() {
            return temp;
        }

        public String getTime() {
            return time;
        }
    }
}
