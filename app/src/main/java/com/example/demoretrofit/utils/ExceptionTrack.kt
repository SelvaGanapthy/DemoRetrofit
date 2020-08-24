package com.example.demoretrofit.utils

import org.json.JSONObject
import retrofit2.Response

object ExceptionTrack {

    private var MemberName: String? = null
    private var MemberId: String? = null
//    var instance: ExceptionTrack? = null

//    companion object {
//        var instance: ExceptionTrack = ExceptionTrack()
//    }
//
//
//    fun getInstance(): ExceptionTrack {
//        if (instance == null)
//            instance = ExceptionTrack()
//        return instance!!
//    }


    //
    fun TrackLog(e: Exception) {
        try {
//            setTrack()
//            Crashlytics.logException(Throwable(e))
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    fun TrackLog(e: Throwable, sUrl: String, vararg data: Int) {
        try {
//            setTrack()
//            if (data.size > 0) {
//                if (data[0] == 1)
//                    Crashlytics.setString("CBSCRASH##" + MemberId!!, sUrl) //Constants.str_ExURL
//            }
//            Crashlytics.logException(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    //
//    /***
//     * Retro Failure from Retro Connect File
//     * @param e
//     * @param RequestType
//     * @param apiUrl
//     */
//
    fun TrackFailure(
        fromUnknownhost: Boolean,
        e: Throwable,
        RequestType: String,
        apiUrl: String
    ) {
        try {
//            setTrack()
//            if (fromUnknownhost)
//                Crashlytics.setString("CBSUNKNOWNHOST##$MemberId", RequestType)
//            else
//                Crashlytics.setString("CBSRETROURL##$MemberId", RequestType)
//            Crashlytics.log(apiUrl)
//            Crashlytics.logException(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    /***
     * Customer Complaint - Image not loading
     * @param e
     * @param key
     * @param imageUrl
     */

    fun TrackImageFailure(e: Exception, key: String, imageUrl: String) {
        try {
//            setTrack()
//            Crashlytics.setString("CBS$key##$MemberId", imageUrl)
//            Crashlytics.logException(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    /***
     * OnReceiveResult - Catch Exception
     * @param e
     * @param RequestType
     * @param response
     */

    fun TrackResponseCatch(e: Exception, RequestType: String, response: Response<*>?) {
        try {
//            setTrack()
//            Crashlytics.setString("CBSRESPONSECATCH##$MemberId", RequestType)
//            if (response != null)
//                Crashlytics.log(Gson().toJson(response.body()))
//            Crashlytics.logException(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    fun TrackFailureStatus(RequestType: String, response: Response<*>?) {
        try {
//            setTrack()
//            Crashlytics.setString("CBSRESPONSEFAILURE##$MemberId", RequestType)
//            if (response != null)
//                Crashlytics.log(Gson().toJson(response.body()))
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    fun TrackChatResponseCatch(e: Exception, TrackChatResponseCatch: JSONObject?) {
        try {
//            setTrack()
//            Crashlytics.setString("CBSCHATCATCH##$MemberId", "")
//            if (TrackChatResponseCatch != null)
//                Crashlytics.log(TrackChatResponseCatch.toString())
//            Crashlytics.logException(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    fun TrackChatCatch(e: Exception) {
        try {
//            setTrack()
//            Crashlytics.setString("CBSCHATCATCH##$MemberId", "")
//            Crashlytics.logException(e)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

//    fun TrackChatCatch(e: SocketIOException) {
//        try {
//            setTrack()
//            Crashlytics.setString("CBSCHATCATCH##$MemberId", "")
//            Crashlytics.logException(e)
//        } catch (e1: Exception) {
//            e1.printStackTrace()
//        }
//
//    }

    fun setTrack() {
//        try {
//            Crashlytics.setUserName("")
//            Crashlytics.setUserIdentifier("")
//            Crashlytics.setString("", "")
//            Crashlytics.log("")
//
//            MemberId = SharedPreferenceData.getInstance().getDataInSharedPreferences(
//                CommunityApplication.getInstance().getContext(),
//                Constants.USER_MATRID
//            )
//            MemberName = SharedPreferenceData.getInstance().getDataInSharedPreferences(
//                CommunityApplication.getInstance().getContext(),
//                Constants.USER_NAME
//            )

//        if (MemberName != null && MemberName != "")
//                Crashlytics.setUserName(MemberName)
//            if (MemberId != null && MemberId != "")
//                Crashlytics.setUserIdentifier(MemberId)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }
}