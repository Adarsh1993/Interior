package com.optic.opticvyu;
/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

public class Constants {

    public static final String APP_PREFERENCE = "app_preferences";
    public static final String APP_Remember = "app_remember";
    public static final String LOGIN_PREFERENCE = "isLoggedIn";
    public static final String CameraSelectionMode = "Camera";
    public static final String Time = "time";
    public static final String USER_ID_PREFERENCE = "user_id";
    public static final String USER_List_Edit = "USer_list_Edit";

    public static final String PROJECT_NAME_PREFERENCE = "project_name";
    public static final String PROJECT_ID_PREFERENCE = "project_Id";
    public static final String PROJECT_DIRECTORY_NAME_PREFERENCE = "project_directory_name";
    public static final String PROJECT_DIRECTORY_ID_PREFERENCE = "project_directory_id";

    public static final String PROJECT_token_type = "token_type";
    public static final String PROJECT_expires_in = "expires_in";
    public static final String timeStamp = "timeStamp";
        public static final String PROJECT_access_token = "access_token";
    public static final String PROJECT_refresh_token = "refresh_token";
    public static final String All_project_Save = "All_project_Add_For_Add_User";

    public static final String User_Email = "Email";
    public static final String User_First_Name = "First_Name";
    public static final String User_Last_Name = "Last_Name";
    public static final String User_ID = "User_id";
    public static final String Free = "Free";
    public static final String const_camera = "const_camera";
    public static final String User2F_auth = "User2F_auth";
    public static final String interior_access = "interior_access";
    public static final String User_code = "User_code";
    public static final String opticvyu_user = "opticvyu_user";
    public static final String User_contact = "User_contact";
    public static final String cctvtimelapse_user = "cctvtimelapse_user";

    public static final String AK = "ak";
    public static final String SK = "sk";
    public static final String region = "region";
    public static final String bucket = "bucket";
    public static final String endpoint ="endpoint";

    public static final String API_Authorization = "Authorization";
    public static final String API_Accept = "Accept";

    public static final String ProjectID = "ProjectID";
    public static final String ProjectName = "ProjectName";
    public static final String CameraID = "cameraID";
    public static final String OrgName = "OrgName";
    public static final String Camera = "CountCamera";
    public static final String ProjectIndex = "index";

    public static final String CameraName = "CameraName";
    public static final String Name_For_share = "OpticVyu.jpeg";
    public static final String materials = "materials";


    public static final String INSPECTION_AREA_NAME = "inspectionAreaName";
    public static final int MAX_NO_OF_IMAGE_SELECTION = 5;
    /*
    Here we all api used in project
    */
    public static final String Get_Oath_Details = "https://api.opticvyu.com/oauth/token";
    public static final String Get_User_Details = "https://api.opticvyu.com/api/user";
    public static final String User_Project = "https://api.opticvyu.com/api/project";
    public static final String User_Project_camera = "https://api.opticvyu.com/api/camera/";

    // some part of api  use in camera in ImagesListIDs
    public static final String ImagesListIDs = "/list/images";
    public static final String LatestImage = "/latest_images";
    public static final String User_Project_camera_presigned_url = "https://api.opticvyu.com/api/image/";
    public static final String User_Project_camera_presigned_url_Cam = "https://api.opticvyu.com/api/camera/";
    public static final String User_add_Record = "https://api.opticvyu.com/api/userlog/";
    public static final String Add_Record = "/add_record";
    public static final String Urllist = "/list/url";

    // Api for Archive [First part camera + below api]
    public static final String Archivetimelist = "/list/images/date/";
    public static final String ContactUS_url = "http://www.opticvyu.com/about#contact-us";

    // Add USer APi first User_Project  +  Email enforcement

    public static final String Add_User_email_enforcement = "/email/enforce";
    // Add user
    public static final String AddUSer = "https://api.opticvyu.com/api/users";
    public static final String Timelapse = "/list/timelapse";
    public static final String Changepassword = "https://api.opticvyu.com/api/user/passwordchange";

    // Add api with  User_Project
    public static final String UserList = "/users";
    public static final String Camera360 = "cam_360";
    // Email Schduler
    public static final String EmailSchdular = "email-scheduler";
    public static final String Custom_time_schdular = "/timelapse/custom";
    public static final String CameraInfo = "/camera/status";
    public static final String LiveImage = "/live-image";
    public static final String Report = "https://scr.opticvyu.com:3000/api/report";
    public static final String Schedule_report = "https://scr.opticvyu.com:3000/api/schedule-report";
    public static final String ScheduleReport = "/save_schedule_report";
    public static final String Custom_timelapse_rest = "/reset_timelapse_setting";
    public static final String GET_SCHEDULE = "/get_scheduledreport";
    public static final String Reset_SCHEDULE = "/reset_schedule_report";
    public static final String StreamUrl = "https://live.opticvyu.com:5443/LiveApp/streams/";
    public static final String InteriorProject = "https://api.opticvyu.com/api/interior/project/";
    public static final String GetZonE = "/get-zone/";
    public static final String GetDrawing = "/get-drawings?zone=";

    public static final String code1 = "LeftUp";
    public static final String code2 = "Up";
    public static final String code3 = "RightUp";
    public static final String code4 = "Left";
    public static final String code5 = "GotoPreset";
    public static final String code6 = "Right";
    public static final String code7 = "LeftDown";
    public static final String code8 = "Down";
    public static final String code9 = "RightDown";
    public static final String zoomIN = "ZoomTele";
    public static final String zoomOut = "ZoomWide";

    public static final String arg1 = "0";
    public static final String arg2 = "1";
    public static final String arg3 = "3";
    public static final String StartURL = "StartURL";
    public static final String StopURL = "StopURL";
    public static final String Base = "http://";
    public static final String CCTV_ID = "CCTV_ID";
    public static final String cctv_pwd = "cctv_pwd";
    public static final String CommonURL = "/cgi-bin/ptz.cgi?action=start&channel=1&";
    public static final String CommonURLStop = "/cgi-bin/ptz.cgi?action=stop&channel=1&";
    public static final String Stop = "Stop";
    public static final String vod = "vod";
    public static int RadioCheck = 0;
    public static String Mode = "";




    /*
      Api for Material and Inventories
     */
      public static final String MaterialInventory = "http://inv.opticvyu.com:3001/explorer";


    /*
     * You should replace these values with your own. See the README for details
     * on what to fill in.
     */
    public static final String COGNITO_POOL_ID = "fQPsKFhRGQ4zSoULz9SaXPBQXkRyXeQ8mmgIXTqm";
    public static final String Secret_ID = "adfCEgbQA2ybpuqgLtoJhAuRedAJBQlp651WDElI";

    /*
     * Note, you must first create a bucket using the S3 console before running
     * the sample (https://console.aws.amazon.com/s3/). After creating a bucket,
     * put it's name in the field below.
     */
    public static final String BUCKET_NAME = "opticvyu-temp1";
   // public static final String BUCKET_NAME = "opticvyu-temp";
   public static String PointStatus = "0";

    // Dashboard
    public static final String Image_GIF = "http://www.animatedimages.org/data/media/111/animated-arrow-image-0061.gif";

    public static String getPictureBucket() {
        return BUCKET_NAME;
    }
}
