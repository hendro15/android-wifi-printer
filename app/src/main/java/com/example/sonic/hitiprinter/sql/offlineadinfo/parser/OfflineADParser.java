package com.example.sonic.hitiprinter.sql.offlineadinfo.parser;

import android.content.Context;
import com.example.sonic.hitiprinter.utility.FileUtility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OfflineADParser {
    public static final String ATTR_AD_Ending_Time = "AD_Ending_Time";
    public static final String ATTR_AD_Item_ID = "AD_Item_ID";
    public static final String ATTR_AD_Photo_Name = "AD_Photo_Name";
    public static final String ATTR_AD_Photo_Path = "AD_Photo_Path";
    public static final String ATTR_AD_Photo_Size = "AD_Photo_Size";
    public static final String ATTR_AD_Photo_Time = "AD_Photo_Time";
    public static final String ATTR_AD_Priority = "AD_Priority";
    public static final String ATTR_AD_Starting_Time = "AD_Starting_Time";
    public static final String ATTR_AD_Video_Name = "AD_Video_Name";
    public static final String ATTR_AD_Video_Path = "AD_Video_Path";
    public static final String ATTR_AD_Video_Size = "AD_Video_Size";
    public static final String ATTR_AD_Video_Time = "AD_Video_Time";
    public static final String ATTR_Country = "Country";
    public static final String ATTR_Info_ID = "Info_ID";
    public static final String ATTR_Number = "Number";
    public static final String ATTR_Type = "Type";
    public static final String ATTR_Version = "Version";
    public static final String NODE_AD_Item = "AD_Item";
    public static final String NODE_Common = "Common";
    public static final String NODE_Offline_AD_Info = "Offline_AD_Info";

    public static OfflineADInfo GetOfflineADInfo(Context context, String strOfflineADInfoXMLPath) {
        OfflineADInfo offlineADInfo = new OfflineADInfo(context);
        try {
            if (!FileUtility.IsFromSDCard(context, strOfflineADInfoXMLPath)) {
                return null;
            }
            InputStream inputStream = new FileInputStream(strOfflineADInfoXMLPath);
            offlineADInfo.SetOfflineADInfoXMLPath(strOfflineADInfoXMLPath);
            Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).getDocumentElement();
            NodeList nodes = root.getElementsByTagName(NODE_Common);
            if (nodes.getLength() <= 0) {
                return null;
            }
            Element CommonElement = (Element) nodes.item(0);
            offlineADInfo.SetInfoID(CommonElement.getAttribute(ATTR_Info_ID));
            offlineADInfo = offlineADInfo;
            offlineADInfo.SetVersion(Integer.valueOf(CommonElement.getAttribute(ATTR_Version)).intValue());
            offlineADInfo = offlineADInfo;
            offlineADInfo.SetType(Integer.valueOf(CommonElement.getAttribute(ATTR_Type)).intValue());
            offlineADInfo = offlineADInfo;
            offlineADInfo.SetNumber(Integer.valueOf(CommonElement.getAttribute(ATTR_Number)).intValue());
            offlineADInfo.SetCountry(CommonElement.getAttribute(ATTR_Country));
            NodeList AD_ItemNodes = root.getElementsByTagName(NODE_AD_Item);
            for (int i = 0; i < AD_ItemNodes.getLength(); i++) {
                Element ADItemElement = (Element) AD_ItemNodes.item(i);
                ADItem adItem = new ADItem();
                adItem.SetADItemID(ADItemElement.getAttribute(ATTR_AD_Item_ID));
                adItem.SetADPriority(ADItemElement.getAttribute(ATTR_AD_Priority));
                adItem.SetADStartingTime(ADItemElement.getAttribute(ATTR_AD_Starting_Time));
                adItem.SetADEndingTime(ADItemElement.getAttribute(ATTR_AD_Ending_Time));
                adItem.SetADVideoName(ADItemElement.getAttribute(ATTR_AD_Video_Name));
                adItem.SetADVideoPath(ADItemElement.getAttribute(ATTR_AD_Video_Path));
                adItem.SetADVideoSize(ADItemElement.getAttribute(ATTR_AD_Video_Size));
                adItem.SetADVideoTime(ADItemElement.getAttribute(ATTR_AD_Video_Time));
                adItem.SetADPhotoName(ADItemElement.getAttribute(ATTR_AD_Photo_Name));
                adItem.SetADPhotoPath(ADItemElement.getAttribute(ATTR_AD_Photo_Path));
                adItem.SetADPhotoSize(ADItemElement.getAttribute(ATTR_AD_Photo_Size));
                adItem.SetADPhotoTime(ADItemElement.getAttribute(ATTR_AD_Photo_Time));
                offlineADInfo.AddADItem(adItem);
            }
            return offlineADInfo;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        } catch (NumberFormatException e4) {
            e4.printStackTrace();
            return null;
        } catch (Exception e5) {
            e5.printStackTrace();
            return null;
        }
    }
}
