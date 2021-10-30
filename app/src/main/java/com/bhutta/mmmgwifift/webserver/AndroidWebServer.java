package com.bhutta.mmmgwifift.webserver;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;

import com.bhutta.mmmgwifift.ShareActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Mikhael LOPEZ on 14/12/2015.
 */
public class AndroidWebServer extends NanoHTTPD {
    public    String url;
    Context context;

   public void   setUrl(String url){
        this.url=url;
       Log.d("TAG", "setUrl: "+url);
    }

    public AndroidWebServer(int port, Context context) {
        super(port);
    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html>" +
                "<head>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<style>\n" +
                ".topnav {\n" +
                "    overflow: hidden;\n" +
                "    background-color: #ff9933;\n" +
                "  }\n" +
                "  \n" +
                "  .topnav a {\n" +
                "    float: right;\n" +
                "    display: block;\n" +
                "    color: #f2f2f2;\n" +
                "    text-align:left;\n" +
                "    padding: 24px ;\n" +
                "    text-decoration: none;\n" +
                "    font-size: 23px;\n" +
                "  }\n" +
                "  \n" +
                "  .topnav a:hover {\n" +
                "    background-color: #ddd;\n" +
                "    color: black;\n" +
                "  }\n" +
                "\n" +
                "  .logo\n" +
                "  {\n" +
                "      width: 80;\n" +
                "      margin-top: 1em;\n" +
                "      margin-left:4em;\n" +
                "    }\n" +
                "  .box\n" +
                "  {\n" +
                "      text-align: center;\n" +
                "      background-color:#99c2ff ;\n" +
                "       border-radius:15px;" +
                "width: auto;\n" +
                "      border: 15px ;\n" +
                "      padding: 20px;\n" +
                "      margin: 20px;\n" +
                "    }\n" +
                "      table {\n" +
                "      font-family: arial, sans-serif;\n" +
                "      border-collapse: collapse;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "    \n" +
                "    td, th {\n" +
                "      border: 1px solid #dddddd;\n" +
                "      text-align: left;\n" +
                "      padding: 8px;\n" +
                "      \n" +
                "    }\n" +
                "     tr:nth-child(even) {\n" +
                "      background-color: #dddddd;\n" +
                "    }\n" +
                "    * {\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                ".button {\n" +
                 "    align-self: center;\n" +
                "    margin: auto;\n" +
                "    margin-top: 30px;"+
                "  display: block;\n" +
                "  width: 200px;\n" +
                "  height: 36px;\n" +
                "  border-radius: 18px;\n" +
                "  background-color: #1c89ff;\n" +
                "  border: solid 1px transparent;\n" +
                "  color: #fff;\n" +
                "  font-size: 16px;\n" +
                "  font-weight: 200;\n" +
                "  cursor: pointer;\n" +
                "  transition: all .1s ease-in-out;\n" +
                "  &:hover {\n" +
                "    background-color: transparent;\n" +
                "    border-color: #fff;\n" +
                "    transition: all .1s ease-in-out;\n" +
                "  }\n" +
                "  \n" +
                "}\n" +
                "\n" +
                "\n" +
                ".loader {\n" +
                "  display: flex;\n" +
                "  justify-content: center;\n" +
                "  align-items: center;\n" +
                "  width: 50px;\n" +
                "  height: 50px;\n" +
                "  background: transparent; \n" +
                "  margin: 30px auto 0 auto;\n" +
                "  border: solid 2px #424242;\n" +
                "  border-top: solid 2px #1c89ff;\n" +
                "  border-radius: 50%;\n" +
                "  opacity: 0;\n" +
                "}\n" +
                "\n" +
                ".check {\n" +
                "  width: 100%;\n" +
                "  height: 100%;\n" +
                "  display: flex;\n" +
                "  flex-direction: column;\n" +
                "  justify-content: center;\n" +
                "  align-items: center;\n" +
                "  transform: translate3d(-4px,50px,0);\n" +
                "  opacity: 0;\n" +
                "  span:nth-child(1) {\n" +
                "    display: block;\n" +
                "    width: 10px;\n" +
                "    height: 2px;\n" +
                "    background-color: #fff;\n" +
                "    transform: rotate(45deg);\n" +
                "  }\n" +
                "  span:nth-child(2) {\n" +
                "    display: block;\n" +
                "    width: 20px;\n" +
                "    height: 2px;\n" +
                "    background-color: #fff;\n" +
                "    transform: rotate(-45deg) translate3d(14px, -4px, 0);\n" +
                "    transform-origin: 100%;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".loader.active {\n" +
                "  animation: loading 2s ease-in-out; \n" +
                "  animation-fill-mode: forwards;\n" +
                "}\n" +
                "\n" +
                ".check.active {\n" +
                "  opacity: 1;\n" +
                "  transform: translate3d(-4px,4px,0);\n" +
                "  transition: all .5s cubic-bezier(.49, 1.74, .38, 1.74);\n" +
                "  transition-delay: .2s;\n" +
                "}\n" +
                "\n" +
                "@keyframes loading {\n" +
                "  30% {\n" +
                "    opacity:1; \n" +
                "  }\n" +
                "  \n" +
                "  85% {\n" +
                "    opacity:1;\n" +
                "    transform: rotate(1080deg);\n" +
                "    border-color: #262626;\n" +
                "  }\n" +
                "  100% {\n" +
                "    opacity:1;\n" +
                "    transform: rotate(1080deg);\n" +
                "     border-color: #1c89ff;\n" +
                "  }\n" +
                "}.datagrid table { border-collapse: collapse; text-align: left; width: 100%; } .datagrid {font: normal 16px/150% Arial, Helvetica, sans-serif; background: #ff9933; overflow: hidden; border: 1px solid #006699; -webkit-border-radius: 3px; -moz-border-radius: 16px; border-radius: 16px;  margin:40px}.datagrid table td, .datagrid table th { padding: 3px 10px; }.datagrid table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 70% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; color:#ffffff; font-size: 15px; font-weight: bold; border-left: 1px solid #0070A8; } .datagrid table thead th:first-child { border: none; }.datagrid table tbody td { color: #00496B; border-left: 1px solid #E1EEF4;font-size: 12px;font-weight: normal; }.datagrid table tbody .alt td { background: #E1EEF4; color: #00496B; }.datagrid table tbody td:first-child { border-left: none; }.datagrid table tbody tr:last-child td { border-bottom: none; }.datagrid table tfoot td div { border-top: 1px solid #006699;background: #E1EEF4;} .datagrid table tfoot td { padding: 0; font-size: 12px } .datagrid table tfoot td div{ padding: 2px; }.datagrid table tfoot td ul { margin: 0; padding:0; list-style: none; text-align: right; }.datagrid table tfoot  li { display: inline; }.datagrid table tfoot li a { text-decoration: none; display: inline-block;  padding: 2px 8px; margin: 1px;color: #FFFFFF;border: 1px solid #006699;-webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 70% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; }.datagrid table tfoot ul.active, .datagrid table tfoot ul a:hover { text-decoration: none;border-color: #006699; color: #FFFFFF; background: none; background-color:#00557F;}div.dhtmlx_window_active, div.dhx_modal_cover_dv { position: fixed !important; }\"\n" +
                 ".alert {" +
                "  padding: 20px;\n" +
                "  background-color: #f44336; /* Red */\n" +
                "  color: white;\n" +
                "  margin-bottom: 15px;\n" +
                "}\n" +
                "\n" +
                "/* The close button */\n" +
                ".closebtn {\n" +
                "  margin-left: 15px;\n" +
                "  color: white;\n" +
                "  font-weight: bold;\n" +
                "  float: center;\n" +
                "  font-size: 22px;\n" +
                "  line-height: 20px;\n" +
                "  cursor: pointer;\n" +
                "  transition: 0.3s;\n" +
                "}\n" +
                "\n" +
                "/* When moving the mouse over the close button */\n" +
                ".closebtn:hover {\n" +
                "  color: orange;\n" +
                "}.msg{" +
                "    color: bisque;\n" +
                "    font-family: auto;\n" +
                "    font-size: large;\n" +
                "    margin: 16px;\n" +
                "    margin-top: 10px;}"+
                  "</style>\n" +
                "</head>";


        if(session.getUri().contains("/setting")) {
                msg= msg+showVideoPage(msg,session);
        }
        else if(session.getUri().contains("/uploadvideo")){
            msg=msg+uploadVideo(msg,session);
        }
        else if(session.getUri().contains("/delete")){
            msg=msg+delete(msg,session);
        }
        else{
           msg= msg+ "<body class=\"bg\" \n" +
                   "   style=\"background: #001f4d;\"/>\n" +
                    " \n" +
                   "   <div class=\"topnav\" id=\"myTopnav\">\n" +
                   "  <a href=\"contact\"><img src=\"https://itqplusglobal.com/others/contact_icon.png\" width=\"23px\">Contact</a>\n" +
                   "  <a href=\"setting\"><img src=\"https://itqplusglobal.com/others/setting_icon.png\" width=\"23px\">Settings</a>\n" +
                   "  <a href=\"uploadvideo.html\"><img src=\"https://itqplusglobal.com/others/video_icon.png\" width=\"23px\">Upload Video</a>\n" +
                   "  <a href=\"home\" class=\"active\"><img src=\"https://itqplusglobal.com/others/home_icon.png\" width=\"23px\">Home</a>\n" +
                   "   <div class=\"logo\">\n" +
                   "    <img src=\"https://itqplusglobal.com/others/logo%20(1).png\">\n" +
                   "</div> \n" +
                   "</div>" +
                    "    </body>";
        }


        return  NanoHTTPD. newFixedLengthResponse(
                msg+"</html>" );

    }

   String  showVideoPage(String msg, IHTTPSession session){
           String targetPath = url;
       Map<String, String> parms = session.getParms();
       Map<String, String> files = new HashMap<>();
       Method method = session.getMethod();
       String httpQueryString = session.getUri();
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss z", Locale.getDefault());
       String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Digitals Screens";

               ArrayList<Uri> shareDataUri = null;
               File dirFile = new File(PATH);
               //}
               String responseString="";
            if (dirFile != null && dirFile.exists() && dirFile.isDirectory()) {
                   if (dirFile.canRead()) {
                       File checkFiles[] = dirFile.listFiles();
                       List<File> lsFiles = new ArrayList<>();
                       for (File file : checkFiles){
                           if (file.canRead()){
                               lsFiles.add(file);
                           } else if (file.getName().equals("emulated")){
                               lsFiles.add(file);
                           }
                       }
                       StringBuilder sb = new StringBuilder();
                       sb.append("<body class=\"bg\" \n" +
                               "   style=\"background: #001f4d;\"/>\n" +
                               " \n" +
                               "   <div class=\"topnav\" id=\"myTopnav\">\n" +
                               "  <a href=\"contact\"><img src=\"https://itqplusglobal.com/others/contact_icon.png\" width=\"23px\">Contact</a>\n" +
                               "  <a href=\"setting\"><img src=\"https://itqplusglobal.com/others/setting_icon.png\" width=\"23px\">Settings</a>\n" +
                               "  <a href=\"uploadvideo.html\"><img src=\"https://itqplusglobal.com/others/video_icon.png\" width=\"23px\">Upload Video</a>\n" +
                               "  <a href=\"home\" class=\"active\"><img src=\"https://itqplusglobal.com/others/home_icon.png\" width=\"23px\">Home</a>\n" +
                               "   <div class=\"logo\">\n" +
                               "    <img src=\"https://itqplusglobal.com/others/logo%20(1).png\">\n" +
                               "</div> \n" +
                               "</div>");
                       sb.append("\n<div class=\"uploadFile\">");
                       sb.append("\n</div>");
                       sb.append("\n<div class=\"datagrid\">"+
                               "  <h2 style=\"text-align: center\">VIDEOS LIST</h2>");

                       sb.append("\n<table>");
                       sb.append("\n<thead>");
                       sb.append("\n<tr>");
                       sb.append("\n<th>File</th>");
                       sb.append("\n<th>Dir ?</th>");
                       sb.append("\n<th>Size</th>");
                       sb.append("\n<th>Date</th>");
                       sb.append("\n<th>Sequence of Playing</th>");
                       sb.append("\n<th>Actions</th>");
                       sb.append("\n</tr>");
                       sb.append("\n</thead>");
                       sb.append("\n<tbody>");
                       if (!httpQueryString.equals("/") && !httpQueryString.equals("/storage/") && dirFile.getParent() != null) {
                           File parent;
                           if (httpQueryString.equals("/0/") || httpQueryString.equals("/emulated/"))
                               parent = new File(dirFile.getParent()).getParentFile();
                           else parent = new File(dirFile.getParent());
                           sb.append("\n\t<tr>");
                           if (parent.getName().equals("0")) parent = parent.getParentFile();
//                           sb.append("\n\t\t<td><a href='" + targetPath + parent + "/'><img src=\"https://itqplusglobal.com/others/video_icon.png\" align='middle' style='padding-right: 10;padding-top: 5;padding-bottom: 5px;' height='50' width='50'/>" + "GoTo Parent" + "</a></td>" +
//                                   "<td>Yes</td>" + "<td>" + getFormatSize(getDirSize(parent)) +
//                                   "</td>" + "<td>" + formatter.format(new Date(parent.lastModified())) + "</td>" +
//                                   "\n\t</tr>");
                       }
                       if (lsFiles != null) {
                           if (httpQueryString.equals("/emulated/")) httpQueryString += "0/";
                           else if (httpQueryString.equals("/0/"))
                               httpQueryString = "/emulated" + httpQueryString;
                           int i = 0;
                           for (File file : lsFiles) {
                               if (i % 2 == 0)
                                   sb.append("\n\t<tr class='alt'>");
                               else
                                   sb.append("\n\t<tr>");
                               if (file.isDirectory())
                                   sb.append("\n\t\t<td><a href='" + targetPath + httpQueryString + file.getName() + "/'><img src=\"https://itqplusglobal.com/others/video_icon.png\" style='padding-right: 10;padding-top: 5;padding-bottom: 5px;' align='middle' height='50' width='50'/>" + file.getName() + "</a></td>" +
                                           "<td>Yes</td>" + "<td>" + getFormatSize(getDirSize(file)) +
                                           "</td>" + "<td>" + formatter.format(new Date(file.lastModified())) + "</td>" +
                                           "<td>"+
                                           "\t\t\t<form name=\"sequence\" id=\"sequence\" enctype=\"multipart/form-data\" method=\"POST\"  action="+targetPath +">\n"+
                                           "  <label for=\"fname\">Set Value:</label><br>\n" +
                                           "  <input type=\"text\" id=\"fname\" name=\"fname\" value=\"\"><br>"+
                                           "\t\t</form>\n" +
                                           "\t\t</td>\n" +
                                           "\t\t<td>\n" +
                                          "         \"<a id=\\\"linkid\\\" href=\\\"https://www.google.com\\\" >Delete Vseo</a>\"+\n" +
                                            "\t\t</td>\n\t</tr>");
                               else {
                                   String srcImg;
                                   switch (fileType(file.getName())){
                                       case 1:
                                           srcImg = "/document_file.png";
                                           break;
                                       case 2:
                                           srcImg = httpQueryString + file.getName();
                                           break;
                                       case 3:
                                           srcImg = "/video.png";
                                           break;
                                       case 4:
                                           srcImg = "/music.png";
                                           break;
                                       case 5:
                                           srcImg = "/compressed.png";
                                           break;
                                       default:
                                           srcImg = "/unknown_file.png";
                                   }
                                   sb.append("\n\t\t<td><lable href='" + targetPath + httpQueryString + file.getName() + "'><img src=\"https://itqplusglobal.com/others/video_icon.png\" style='padding-right: 10;padding-top: 5;padding-bottom: 5px;' align='middle' height='50' width='50' />" + file.getName() + "</a></td>" +
                                           "<td> </td>" + "<td>" + getFormatSize(file.length()) +
                                           "</td>" +
                                           "<td>" + formatter.format(new Date(file.lastModified())) + "</td>" +
                                           "<td>"+
                                           "\t\t\t<form name=\"sequence\" id=\"sequence\" enctype=\"multipart/form-data\" method=\"POST\"  action="+targetPath +">\n"+
                                           "  <label for=\"fname\">Set value:</label><br>\n" +
                                           "  <input type=\"text\" id=\"fname\" name=\"fname\" value=\"\"><br>"+
                                           "\t\t</form>\n" +
                                           "\t\t</td>\n" +
                                           "\t\t<td>\n" +
                                          "<a id=\"linkid\" href=\"delete"+file.getAbsolutePath()+"\" >Delete Video</a>"+
                                           "\t\t</td></tr>");
                               }
                               i++;
                           }
                       }
                       sb.append("\n</tbody>");
                       sb.append("\n</table>");
                       sb.append("\n</div>");
                       sb.append("\n</body>");
                                responseString = sb.toString();
             //          sendResponse(200, responseString, false, false);
                   }
               } else if (dirFile != null && dirFile.exists() && dirFile.isFile()){
                   responseString = dirFile.getAbsolutePath();
               //    sendResponse(200, responseString, true, false);
               } else {
//                   sendResponse(404, "<b>The Requested resource not found ...." +
//                           "Usage: http://" + addressport + ":" + port + "</b>", false, false);
               }
            msg=    msg+responseString;


       return  msg  ;
   }





    String  delete(String msg, IHTTPSession session){

        Map<String, String> parms = session.getParms();
        Map<String, String> files = new HashMap<>();
        Method method = session.getMethod();
        String uri = session.getUri();
        msg +=  "<body class=\"bg\" \n" +
                "   style=\"background: #001f4d;\"/>\n" +
                " \n" +
                "   <div class=\"topnav\" id=\"myTopnav\">\n" +
                "  <a href=\"contact\"><img src=\"https://itqplusglobal.com/others/contact_icon.png\" width=\"23px\">Contact</a>\n" +
                "  <a href=\"setting\"><img src=\"https://itqplusglobal.com/others/setting_icon.png\" width=\"23px\">Settings</a>\n" +
                "  <a href=\"uploadvideo.html\"><img src=\"https://itqplusglobal.com/others/video_icon.png\" width=\"23px\">Upload Video</a>\n" +
                "  <a href=\"home\" class=\"active\"><img src=\"https://itqplusglobal.com/others/home_icon.png\" width=\"23px\">Home</a>\n" +
                "   <div class=\"logo\">\n" +
                "    <img src=\"https://itqplusglobal.com/others/logo%20(1).png\">\n" +
                "</div> \n" +
                "</div><div style=\"text-align: center\" >"  ;




                try{
                String convertedPath= uri.replace("/delete","");
                String PATH = convertedPath;
                File directory = new File(PATH);
                if (directory.exists()){
                    if(directory.delete()){
                        msg += "<div  class=\"msg\" ><h4> Video deleted successfully....... "+"!</h4></div>"+
                                "<a href=\""+url+"/setting\" class=\"closebtn\">Back To Home </a></div>";
                    }
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }else{
                    msg += "<div class=\"msg\"  ><h4> Video Not Found....... "+"!</h4>"+
                            "<a href=\""+url+"/setting\" class=\"closebtn\">Back To Home </a></div>";
                    ;
                }


                } catch (Exception ioe) {
                    // Response for failed
                    msg += "<p class=\"alert\" >" + ioe.getMessage() + "!</p>";

                }
            //}
//            else {
//                msg += "<p class=\"error\" >Server is not delete video.</p>";
//            }


        return  msg +
                "  </div>\n" +
                "\n" +
                "</body>\n" ;
    }


    String  uploadVideo(String msg, IHTTPSession session){
        Map<String, String> parms = session.getParms();
        Map<String, String> files = new HashMap<>();
        Method method = session.getMethod();
        String uri = session.getUri();
        msg += "<body class=\"bg\" \n" +
                "   style=\"background: #001f4d;\"/>\n" +
                " \n" +
                "   <div class=\"topnav\" id=\"myTopnav\">\n" +
                "  <a href=\"contact\"><img src=\"https://itqplusglobal.com/others/contact_icon.png\" width=\"23px\">Contact</a>\n" +
                "  <a href=\"setting\"><img src=\"https://itqplusglobal.com/others/setting_icon.png\" width=\"23px\">Settings</a>\n" +
                "  <a href=\"uploadvideo.html\"><img src=\"https://itqplusglobal.com/others/video_icon.png\" width=\"23px\">Upload Video</a>\n" +
                "  <a href=\"home\" class=\"active\"><img src=\"https://itqplusglobal.com/others/home_icon.png\" width=\"23px\">Home</a>\n" +
                "   <div class=\"logo\">\n" +
                "    <img src=\"https://itqplusglobal.com/others/logo%20(1).png\">\n" +
                "</div> \n" +
                "</div>" +
                " <div class=\"box \">\n" +
                "    <form style=\"text-align-last: center;\" name=\"video_form\" id=\"video_form\" enctype=\"multipart/form-data\" method=\"POST\"  action=\"uploadvideo\">\n" +
                "      <img width=\"150\" height=\"150\" src=\"https://itqplusglobal.com/others/image_2021_08_25T11_37_37_735Z.png\">\n" +
                "      <h1 class=\"heading\" >Digital Screens</h1>\n" +
                "      <p class=\"para\" >Please upload video by selecting file from system.</p>\n" +
                "      <div >\n" +
                "      \n" +
                "      <input id=\"file\" class=\"file\" name=\"file\" type=\"file\" />\n" +
                "          \n" +
                "      </div>" ;


        if (Method.POST.equals(method) || Method.PUT.equals(method)) {
            try {
                session.parseBody(files);
            } catch (Exception ioe) {
                msg += "<p>IOException, " + ioe.getMessage() + "!</p>";
            }


            if ("/uploadvideo".equalsIgnoreCase(uri)) {
                String filename = parms.get("file");
                String tmpFilePath = files.get("file");
                if (null == filename || null == tmpFilePath) {
                    // Response for invalid parameters
                }
                String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Digitals Screens";
                File directory = new File(PATH);
                if (! directory.exists()){
                    directory.mkdir();
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }
                File dst = new File(PATH +"/"+ filename);
                if (dst.exists()) {
                    // Response for confirm to overwrite
                }
                File src = new File(tmpFilePath);
                try {
                    InputStream in = new FileInputStream(src);
                    OutputStream out = new FileOutputStream(dst);
                    byte[] buf = new byte[10000000];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    msg += "<p class=\"success\" >Video saved successfully....... "+"!</p>";

                } catch (Exception ioe) {
                    // Response for failed
                    msg += "<p class=\"error\" >" + ioe.getMessage() + "!</p>";

                }
            } else {
                msg += "<p class=\"error\" >Server is not receiving video.</p>";
            }

        }
        return  msg + "<input class=\"button\"  id=\"Upload\" name=\"Upload\" value=\"Submit\" type=\"submit\"  ></input>\n" +
                "      \n" +
                "    </form>\n" +
                "    \n" +
                "  </div>\n" +
                "\n" +
                "</body>\n" ;
    }

    private byte fileType(String filename){
        String[] temp = filename.split("\\.");
        switch (temp[temp.length - 1].toLowerCase()){
            case "txt":
            case "xlsx":
            case "xls":
            case "gslides":
            case "ppt":
            case "pptx":
            case "pdf":
            case "doc":
            case "docx":
            case "docm":
                return 1;
            case "gif":
            case "tif":
            case "ico":
            case "jpg":
            case "jpeg":
            case "png":
            case "tiff":
                return 2;
            case "3gp":
            case "avi":
            case "flv":
            case "m4v":
            case "mkv":
            case "mov":
            case "mng":
            case "mpeg":
            case "mpg":
            case "mpe":
            case "mp4":
            case "wmv":
            case "webm":
                return 3;
            case "mp1":
            case "mp2":
            case "mp3":
            case "aac":
            case "wma":
            case "amr":
            case "wav":
                return 4;
            case "zip":
            case "7z":
            case "cab":
            case "gzip":
            case "bin":
            case "rar":
            case "tar":
            case "iso":
                return 5;
            default:
                return 0;
        }
    }

    private String getFormatSize(double size){
        final double KB = 1024.00;
        final double MB = 1024.00 * KB;
        final double GB = 1024.00 * MB;
        if (size/KB < 0.90) {
            return String.format( Locale.getDefault(),"%.2f",size) + " B";
        } else if (size/MB < 0.90) {
            return String.format( Locale.getDefault(),"%.2f",size/KB) + " KB";
        } else if (size/GB  < 0.90) {
            return String.format( Locale.getDefault(),"%.2f", size/MB) + " MB";
        } else {
            return String.format( Locale.getDefault(),"%.2f",size/GB) + " GB";
        }
    }

    private long getDirSize(File dir){
        long size = 0;
        if (dir.isDirectory() && dir.canRead()){
            for (File file : dir.listFiles()){
                if (file.isFile() && file.canRead()){
                    size += file.length();
                } else {
                    size += getDirSize(file);
                }
            }
        } else if (dir.isFile() && dir.canRead()){
            size += dir.length();
        } else if (dir.getName().equals("emulated")){
            size += getDirSize(new File(dir.getAbsolutePath() + "/0"));
        }
        return size;
    }

}
