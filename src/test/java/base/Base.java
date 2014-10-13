package base;

import jdk.nashorn.internal.runtime.ECMAException;
import org.sikuli.api.*;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.basics.Settings;
import org.sikuli.natives.*;
import org.sikuli.script.Region;
import org.sikuli.script.Region.*;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;
import sun.reflect.annotation.ExceptionProxy;
import org.sikuli.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Properties;

/**
 * Created by KHallbeck on 6/28/14.
 */
public class Base {
    Region build = new Region(1022, 225, 249, 344);
    Screen s = new Screen();
    Region r = new Region(944, 4, 418, 759);//done
    ScreenLocation bank = new DesktopScreenLocation(1118, 345);
    ScreenLocation vipNO = new DesktopScreenLocation(1294, 144);
    ScreenLocation bookmarkButton = new DesktopScreenLocation(952, 35);
    Region bluestacks = new Region(18, 16, 1077, 247);//done
    Region topapps = new Region(24, 46, 157, 49);
    Region goBackBS = new Region(26, 595, 215, 50);
    Region e = new Region(35, 545, 164, 291);
    Region drag = new Region(952, 127, 408, 444);
    ScreenLocation Collect_OK = new DesktopScreenLocation(1158, 395);
    ScreenRegion topBarKingdom = new DesktopScreenRegion(952, 35, 221, 39);
    ScreenRegion app = new DesktopScreenRegion(944, 4, 418, 759);//done
    Region fullScreen = new Region(1198,705,166,63);
    ScreenLocation bigX = new DesktopScreenLocation(1331, 46);
    ScreenLocation loginArea = new DesktopScreenLocation(1046, 263);
    ScreenLocation chest = new DesktopScreenLocation(983, 500);
    ScreenLocation all = new DesktopScreenLocation(1150, 150);
    ScreenLocation top = Relative.to(all).below(40).getScreenLocation();
    ScreenLocation bottom = Relative.to(Collect_OK).below(100).getScreenLocation();//was 400
    ScreenLocation goLeft = Relative.to(all).below(275).left(160).getScreenLocation();
    ScreenLocation goRight = Relative.to(all).below(275).right(160).getScreenLocation();
    ScreenLocation foodBeg = Relative.to(all).below(20).left(110).getScreenLocation();
    ScreenLocation foodEnd = Relative.to(all).below(20).right(70).getScreenLocation();
    ScreenLocation woodBeg = Relative.to(all).below(85).left(110).getScreenLocation();
    ScreenLocation woodEnd = Relative.to(all).below(85).right(70).getScreenLocation();
    ScreenLocation stoneBeg = Relative.to(all).below(150).left(110).getScreenLocation();
    ScreenLocation stoneEnd = Relative.to(all).below(150).right(70).getScreenLocation();
    ScreenLocation oreBeg = Relative.to(all).below(215).left(110).getScreenLocation();
    ScreenLocation oreEnd = Relative.to(all).below(215).right(70).getScreenLocation();
    ScreenLocation silverBeg = Relative.to(all).below(280).left(110).getScreenLocation();
    ScreenLocation silverEnd = Relative.to(all).below(280).right(70).getScreenLocation();
    //ScreenRegion launchApp = new DesktopScreenRegion(1537,21,206,570);
    //ScreenRegion Login = new DesktopScreenRegion(1517,387,48,23);
    ScreenRegion quests = new DesktopScreenRegion(958, 158, 397, 274);
    //ScreenLocation login = new DesktopScreenLocation(1517,387);
    //ScreenLocation password = new DesktopScreenLocation(1476,457);
    //ScreenLocation smallX = new DesktopScreenLocation(1829,40);



    //target offset is r.getCenter().right(x).below(y)).
    static Mouse mouse = new DesktopMouse();
    Keyboard kb = new DesktopKeyboard();
    String imgRoot = "C:\\games\\GOW\\src\\test\\resources\\";
    //String appRoot = "C:\\ProgramData\\BlueStacks\\UserData\\Library\\My Apps\\";
    String appRoot = "C:\\Users\\Public\\Desktop\\";


    public void startGOW(String login, String password, String emailFrom, String emailTo, String emailPassword) {
        //checkAppStatus();
        try {
            startup();
            waitForPhone();
            enterLogin(login);
            enterPassword(password);
            clickLogin(login, emailFrom, emailTo, emailPassword);
            if (doesItExist(imgRoot + "LoginFailed.png", .085, r)) {
                tryLoginAgain(login, password, emailFrom, emailTo, emailPassword);
            }
        } catch (Exception e) {
            print("Error: " + e);
        }
    }

    public void checkAppStatus() throws Exception {
        /*ScreenRegion phoneExists = app.find(new ImageTarget(new File(imgRoot+"phone.png")));
            if(phoneExists != null) {
                exitGOW();
                Wait(15);
            }*/
        if (doesItExist(imgRoot + "phone.png", .085, r)) {
            exitGOW();
            Wait(15);
        } else {
        }
    }

    public void tryLoginAgain(String login, String password, String emailFrom, String emailTo, String emailPassword) throws Exception {
        if (doesItExist(imgRoot + "LoginFailed.png", .085, r)) {
            r.click(r.find(imgRoot + "LoginFailed.png").getBottomRight());
            print("login failed: " + login);
            closeGOW();
            quitGOW();
            startup();
            waitForPhone();
            enterLogin(login);
            enterPassword(password);
            clickLogin(login, emailFrom, emailTo, emailPassword);
        }

    }

    public void launchApp() throws Exception {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        print("Current relative path is: " + s);
        //String appToLaunch = ("Game Of War.lnk");
        String appToLaunch = ("Start BlueStacks.lnk");
        print("Current app to open is: " + appToLaunch);
        //Runtime rt = Runtime.getRuntime();
        // Process p = rt.exec(appToLaunch);
        File file = new File(appRoot + appToLaunch);
        //first check if Desktop is supported by Platform or not
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        print("Current app to open is: " + file);
        if (file.exists()) desktop.open(file);
        //until I get the right shortcut <sigh>
        Wait(45);
        clickGOW();
    }
    public void launchBS() throws Exception {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        print("Current relative path is: " + s);
        String appToLaunch = ("Start BlueStacks.lnk");
        print("Current app to open is: " + appToLaunch);
        File file = new File(appRoot + appToLaunch);
        //first check if Desktop is supported by Platform or not
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        print("Current app to open is: " + file);
        if (file.exists()) desktop.open(file);
    }

    public void clickGOW() throws Exception {
        bluestacks.wait(imgRoot + "GOWicon.png", 30);
        Wait(2);
        bluestacks.click(bluestacks.find(imgRoot + "GOWicon.png"));
    }

    public void startup() throws Exception {
        if (doesItExist(imgRoot + "GOWicon.png", .85, bluestacks)) {
            clickGOW();
        }
        else if (doesItExist(imgRoot + "1408112219147.png", .90, fullScreen)) {
            fullScreen.click(fullScreen.find(imgRoot + "1408112219147.png"));
            if (doesItExist(imgRoot + "GOWicon.png", .85, bluestacks)) {
                clickGOW();
            }
        }
            else if (!doesItExist(imgRoot + "GOWicon.png", .85, bluestacks) && doesItExist(imgRoot + "BSGoBack.png", .85, goBackBS)) {
            goBackBS.click(goBackBS.find(imgRoot + "BSGoBack.png"));
            if (doesItExist(imgRoot + "GOWicon.png", .85, bluestacks)) {
                clickGOW();
            }
        } else if (doesItExist(imgRoot + "BSTopApps.png", .85, topapps)) {
            goBackBS.click(goBackBS.find(imgRoot + "BSGoBack.png"));
            if (doesItExist(imgRoot + "GOWicon.png", .85, bluestacks)) {
                clickGOW();
            }
        }
        else {
            print("couldn't find the GOW icon. trying to logout");
            closeGOW();
            dontQuitGOW();
            exitGOW();
            //killBlueStacks();
            Wait(3);
            launchApp();
        }
    }


    public boolean doesItExist(String lookFor, Double compareScore, Region regionLookIn) throws Exception {
        Boolean result = false;
        try {
            Double score = regionLookIn.find(lookFor).getScore();
            print("score is: " + score);
            BigDecimal d1 = BigDecimal.valueOf(score);
            BigDecimal d2 = BigDecimal.valueOf(compareScore);
            Integer retval = d1.compareTo(d2);
            print("result is: " + (retval > 0.8) + " " + lookFor);
            if (retval > 0.8) {
                result = true;
            }
        } catch (Exception e) {
            print("result is: false" + lookFor);
            result = false;
        }
        return result;
    }

    //lots o stuff
    public void Wait(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            print("Sleep exception...its a nightmare");
        }
    }

    public void print(String text) {
        System.out.println(text);
    }

    public void checkAppStillLoggedIn(String login) throws Exception {
        if (!doesItExist(imgRoot + "enter_email.png", 0.85, r)) {
            getPastSplash();
            exitGOW();
            Wait(16);
        }
        enterLogin(login);
    }

    public void enterLogin(String login) throws Exception {
        if (doesItExist(imgRoot + "enter_email.png", 0.85, r)) {
            Wait(2);
            ScreenLocation typeEmail = Relative.to(loginArea).right(50).below(5).getScreenLocation();
            mouse.hover(typeEmail);
            mouse.doubleClick(typeEmail);
            Wait(3);
            kb.type(login);
            kb.type(Key.ENTER);
            print("login typed ");
            Wait(2);
        } else {
            checkAppStillLoggedIn(login);
        }
    }


    public void enterPassword(String password) throws Exception {
        Wait(2);
        ScreenLocation typePassword = Relative.to(loginArea).right(50).below(55).getScreenLocation();
        mouse.hover(typePassword);
        Wait(2);
        mouse.doubleClick(typePassword);
        Wait(2);
        kb.type(password);
        kb.type(Key.ENTER);
        print("password typed");
    }

    public void clickLogin(String login, String emailFrom, String emailTo, String password) throws Exception {
        Wait(2);
        r.click(r.find(imgRoot + "Login.png"));
        email(login, emailFrom, emailTo, password);
    }

    public void clickTopX() throws Exception {
        ScreenLocation f = Relative.to(bigX).right(10).getScreenLocation();
        mouse.click(f);
    }

    public void clickX() throws Exception {
        ScreenLocation e = Relative.to(bigX).left(0).below(20).getScreenLocation();
        mouse.click(e);
    }

    public void clickAnother1X() throws Exception {//not used
        ScreenLocation f = Relative.to(bigX).left(20).below(45).getScreenLocation();
        mouse.click(f);
    }

    public void clickAnotherX() throws Exception {
        ScreenLocation f = Relative.to(bigX).left(20).below(50).getScreenLocation();
        mouse.click(f);
    }

    public void clickMidX() throws Exception {
        ScreenLocation e = Relative.to(bigX).below(80).left(15).getScreenLocation();
        mouse.click(e);
    }

    public void clickLowerX() throws Exception {
        ScreenLocation e = Relative.to(bigX).below(135).left(25).getScreenLocation();
        mouse.click(e);
    }

    public void goldStore() {
        try {
            ScreenRegion goldStoreClose = app.find(new ImageTarget(new File(imgRoot + "TheGoldStore.png")));
            ScreenLocation goldStoreCloseLocation = goldStoreClose.getUpperRightCorner();
            print("looking for Gold Store close");
            mouse.click(goldStoreCloseLocation);
            Wait(1);
        } catch (Exception e) {
            print("cant find Gold Store close");
        }
    }


    public boolean lookForGameHelp() throws Exception {
        //while game quests banner is not there.
        //if game quests banner IS NOT there return false
        //game quests banner went away. looking for quest banner
        return (doesItExist(imgRoot + "splash_gone.png", .85, r));
    }

    public void getRidOfGoldScreen() throws Exception {
        if (doesItExist(imgRoot + "cornerX.png", .80, r)) {
            r.click(imgRoot + "cornerX.png");
            print("clicking corner button");
        }
    }


    public void getPastSplash() throws Exception {
        Wait(18);
        print("waiting");
        print("splash???: " + lookForGameHelp());
        if (!lookForGameHelp()) {
            Wait(1);
            clickTopX();
            print("clicked top x");
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            Wait(1);
            clickX();
            print("clicked next to top x");
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            Wait(1);
            clickMidX();
            print("clicked mid x");
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            Wait(1);
            clickAnotherX();
            print("clicked another x");
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            Wait(1);
            clickLowerX();
            print("clicked lower x");
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            Wait(1);
            getRidOfGoldScreen();
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            getRidOfGoldScreen();
            if (lookForGameHelp()) {
                assert true;
            }
        }
        if (!lookForGameHelp()) {
            townView();
            if (lookForGameHelp()) {
                assert true;
            }
        }

    }

    public void waitForPhone() throws Exception {
        try {
            print("waiting for login page");
            r.wait(imgRoot + "enter_email.png", 100);
        } catch (Exception e) {
            print("couldn't find the login. maybe I was already logged in.");
            try {
                closeGOW();
                dontQuitGOW();
                exitGOW();
                startup();
                Wait(10);
            } catch (Exception f) {
                killBlueStacks();
                Wait(20);
                print("Killed BlueStacks. I waited 20 seconds before relaunching");
                startup();
            }
        }
    }

    public void treasureChest() throws Exception {
        //click treasure chest
        print("looking for treasure chest");
        ScreenLocation t = Relative.to(chest).right(20).below(10).getScreenLocation();
        Wait(1);
        mouse.hover(t);
        Wait(1);
        mouse.click(t);
        Wait(1);
        clickMiddleButtonTC();
        Wait(1);
    }


    public void clickMiddleButtonTC() throws Exception {//used for chest OK and collect
        mouse.hover(Collect_OK);
        Wait(1);
        if (doesItExist(imgRoot + "collect.png", .75, r)) {
            mouse.click(Collect_OK);
        }
        Wait(1);
        if (doesItExist(imgRoot + "OK.png", .75, r)) {
            r.click(r.find(imgRoot + "OK.png"));
            if (doesItExist(imgRoot + "OK.png", .75, r)) {
                r.click(r.find(imgRoot + "OK.png"));
            }
        } else if (doesItExist(imgRoot + "collect.png", .75, r)) {
            r.click(r.find(imgRoot + "collect.png"));
            if (doesItExist(imgRoot + "collect.png", .75, r)) {
                r.click(r.find(imgRoot + "collect.png"));
            }
        }
        if (!lookForGameHelp()) {
            if (doesItExist(imgRoot + "GoldX.png", 0.85, r)) {
                r.click(r.find(imgRoot + "GoldX.png"));
            }
        }
    }


    public void clickMiddleButtonATC() throws Exception {//used for Athenas chest OK and collect
        Wait(1);
        if (doesItExist(imgRoot + "OKAthena.png", .75, r)) {
            r.hover(imgRoot + "OKAthena.png");
            r.click(r.find(imgRoot + "OKAthena.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "OKAthena.png", .75, r)) {
            r.hover(imgRoot + "OKAthena.png");
            r.click(r.find(imgRoot + "OKAthena.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "collectAthena.png", .75, r)) {
            r.hover(imgRoot + "collectAthena.png");
            r.click(r.find(imgRoot + "collectAthena.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "collectAthena.png", .75, r)) {
            r.hover(imgRoot + "collectAthena.png");
            r.click(r.find(imgRoot + "collectAthena.png"));
        }
/*        mouse.hover(Collect_OK);
        Wait(1);
        mouse.click(Collect_OK);
        Wait(1);*/
        if (doesItExist(imgRoot + "OK.png", .75, r)) {
            r.click(r.find(imgRoot + "OK.png"));
            if (doesItExist(imgRoot + "OK.png", .75, r)) {
                r.click(r.find(imgRoot + "OK.png"));
            }
        } else if (doesItExist(imgRoot + "collect2.png", .75, r)) {
            r.click(r.find(imgRoot + "collect2.png"));
            if (doesItExist(imgRoot + "collect2.png", .75, r)) {
                r.click(r.find(imgRoot + "collect2.png"));
            }
        }
        if (!lookForGameHelp()) {
            if (doesItExist(imgRoot + "GoldX.png", 0.85, r)) {
                r.click(r.find(imgRoot + "GoldX.png"));
            }
        }
    }


    public void athenaTreasureChest() throws Exception {
        print("looking for Athena's treasure chest");
        ScreenLocation AthenaTC = Relative.to(chest).right(100).below(10).getScreenLocation();
        Wait(1);
        if (doesItExist(imgRoot + "athenaTC.png", .75, r)) {
            mouse.hover(AthenaTC);
            mouse.click(AthenaTC);
            print("athena treasure chest");
        }
        Wait(1);
        if (doesItExist(imgRoot + "athenaTC.png", .74, r)) {
            mouse.hover(AthenaTC);
            mouse.click(AthenaTC);
            print("athena treasure chest");
        }
        Wait(1);
        clickMiddleButtonATC();
        Wait(1);
        if (doesItExist(imgRoot + "GoldX.png", 0.85, r)) {
            r.click(r.find(imgRoot + "GoldX.png"));
        }
    }

    public void attack(String troops) throws Exception {
        bookmarks();
        clickBookmarksFavorite();
        clickBookmarksFriend();
        clickBookmarksGoTo();
        clickBank();
        clickAttack(troops);
    }

    public void clickAttack(String troops) throws Exception {
        if (doesItExist(imgRoot + "attack.png", 0.90, r)) {
            r.click(r.find(imgRoot + "attack.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "troopsToSend.png", 0.90, r)) {
            r.click(r.find(imgRoot + "troopsToSend.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "typeTroopsToSend.png", 0.90, r)) {
            r.click(r.find(imgRoot + "typeTroopsToSend.png"));
            kb.type(troops);
        }
        Wait(1);
        if (doesItExist(imgRoot + "doneTypingTroops.png", 0.90, r)) {
            r.click(r.find(imgRoot + "doneTypingTroops.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "march.png", 0.90, r)) {
            r.click(r.find(imgRoot + "march.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "continueNoHero.png", 0.90, r)) {
            r.click(r.find(imgRoot + "continueNoHero.png"));
        }
    }

    public void freeSpin() throws Exception {
        if (doesItExist(imgRoot + "aboutGOW.png", 0.88, r)) {
            r.click(r.find(imgRoot + "aboutGOW.png"));
            Wait(1);
            r.click(r.find(imgRoot + "aboutGOW.png"));
            print("clicked + in menu");
            Wait(2);
            r.click(r.find(imgRoot + "casino.png"));
        }
        if (doesItExist(imgRoot+"OKcasino.png",.85,r)){
            r.click(r.find(imgRoot + "OKcasino.png"));
        }
        Wait(1);
        if (doesItExist(imgRoot + "FreeSpin.png", 0.88, r)) {
            try {
                r.doubleClick(r.find(imgRoot + "freeSpin.png"));
                Wait(5);
                try {
                    if (chest != null) {
                        r.click(r.find(imgRoot + "TreasureCasino.png"));
                        Wait(2);
                        r.click(r.find(imgRoot + "collectSpinChest.png"));
                        if (doesItExist(imgRoot + "collectSpinChest_alt.png", .85, r)) {
                            r.click(r.find(imgRoot + "collectSpinChest_alt.png"));
                        }
                    }
                } catch (Exception e) {
                    print("no treasure chest");
                }
            } catch (Exception e) {
                print("no treasure chest");
            }
        }
        else{
                print("couldn't find free spin.");
            }
        townView();
    }
    public void freeBuild()throws Exception{
        Wait(1);
        if(doesItExist(imgRoot + "Free.png",0.90,r)) {
            r.click(r.find(imgRoot + "Free.png"));
        }
    }

    public void townView() throws Exception {
        //Town View
        if(doesItExist(imgRoot + "town.png",.85,r)){
            print("looking for Townview");
            r.click(r.find(imgRoot + "town.png"));
            Wait(1);
            while(!doesItExist(imgRoot + "splash_gone.png",.85,r)){
                r.click(r.find(imgRoot + "town.png"));
                Wait(1);
            }
        } else  {
            print("in town view already");
        }
    }

    public void kingdomView() throws Exception {
        //To Kingdom View
        if(doesItExist(imgRoot + "kingdom.png",.85,r)){
         print("looking for kingdomview");
            r.click(r.find(imgRoot + "kingdom.png"));
            Wait(1);
            while(doesItExist(imgRoot + "splash_gone.png",.85,r)){
                try {
                    if (doesItExist(imgRoot + "kingdom.png", .85, r)) {
                        r.click(r.find(imgRoot + "kingdom.png"));
                        Wait(3);
                    }
                }catch(Exception e){}
            }
        } else {
            print("in kingdom view already");
        }
    }
    public void allianceGiftsButton() throws Exception {
        //Alliance button (gifts)
        if(doesItExist(imgRoot + "giftsAvailable.png",.80,r)) {
            r.click(r.find(imgRoot + "giftsAvailable.png"));
            r.click(r.find(imgRoot + "giftsAvailable.png"));
//accept gifts Gift box
            print(""+doesItExist(imgRoot + "gifts.png",.85,r));
            while (doesItExist(imgRoot + "gifts.png",.80,r)) {
                r.hover(imgRoot + "gifts.png");
                r.click(r.find(imgRoot + "gifts.png"));
                Wait(1);
            }
        //while open gift exists
            while (doesItExist(imgRoot + "open.png",.80,r)) {
            //Open Gift
                r.click(r.find(imgRoot + "open.png"));
                Wait(2);
                if (doesItExist(imgRoot + "clear.png",.80,r)) {
                    r.click(r.find(imgRoot + "clear.png"));
                }
                Wait(1);
            }
            //Clear Gift
            if (doesItExist(imgRoot + "clear.png",.80,r)) {
                r.click(r.find(imgRoot + "clear.png"));
                Wait(1);
            }
            print("no more gifts to open");
            townView();
        }
    }

    public void doQuests() throws Exception {
        //Do Quests
        clickQuests();
        clickQuests();
        allianceQuests();
        collectQuests();
        startQuests();
        //Daily Quests -
        clickQuests();
        clickQuests();
        dailyQuests();
        collectQuests();
        startQuests();
        clickQuests();
        clickQuests();
        vipQuests();
        townView();
    }
    public void clickQuests() throws Exception{
        try {
            r.click(r.find(imgRoot + "quests.png"));
            print("Clicking Quests");
        } catch (Exception e) {
            print("cant find Quests button");
        }
    }
    //Alliance Quests
    public void allianceQuests() throws Exception {

        Wait(2);
        if(doesItExist(imgRoot + "AllianceQuests.png",0.80,r)){
            r.click(r.find(imgRoot + "AllianceQuests.png"));
            print("Clicking Alliance Quests");
        }
        else{
            print("cant find Alliance Quests button");
        }
    }
    //Daily Quests
    public void dailyQuests() throws Exception {
        if(doesItExist(imgRoot + "DailyQuests.png",0.80,r)) {
            Wait(2);
            r.click(r.find(imgRoot + "DailyQuests.png"));
            print("Clicking Daily Quests");
        }
        else{
            print("cant find Daily Quests button");
        }
    }
    //VIP Quests

    public void vipQuests() throws Exception {
        if(doesItExist(imgRoot + "VIPQuests.png",0.80,r)) {
            r.hover(imgRoot + "VIPQuests.png");
            r.doubleClick(r.find(imgRoot + "VIPQuests.png"));
            Wait(1);
        //Boolean runVIP = isVipINActive();
            print("check if vip NOT active pop up: "+doesItExist(imgRoot +"VIPNO.png",0.80,r));
            if(doesItExist(imgRoot +"VIPNO.png",0.80,r)){
                vipNotActive();
            }
        //if(!doesItExist(imgRoot +"VIPNO.png",0.80,r)){
            else{
                collectQuests();
                startQuests();
            }
        }
    }
    public boolean isVipINActive()throws Exception{//This will tell us if the VIP is NOT active
       Boolean result = doesItExist(imgRoot +"VIPNO.png",0.80,r);
        return result;
    }
    public void vipNotActive() throws Exception{
        print("Is the close button there? " + doesItExist(imgRoot +"VIPNO.png",0.85,r));
        if (doesItExist(imgRoot +"VIPNO.png",0.80,r)) {
                Wait(1);
                ScreenLocation e = Relative.to(vipNO).right(11).below(17).getScreenLocation();
                mouse.hover(e);
                mouse.click(e);
        }
        Wait(2);
        if (doesItExist(imgRoot +"VIPNO.png",0.80,r)) {
            Wait(1);
            ScreenLocation e = Relative.to(vipNO).right(11).below(17).getScreenLocation();
            mouse.hover(e);
            mouse.click(e);
        }
    }
    //Collect Quest

    public void collectQuests() throws Exception {
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)){
            print("quests not running");
            if (doesItExist(imgRoot + "collectLocation.png",.80,r)) {
                print("collect quests");
                r.click(r.find(imgRoot + "collectLocation.png"));
                heroUpgrade();
            }
        }
        if(doesItExist(imgRoot + "AutoComplete.png",.90,r)){
            print("autocomplete quests");
        //while AutoCommplete exists
            while (doesItExist(imgRoot + "AutoComplete.png",.90,r)) {
            //AutoComplete
                print("autocompleting quests");
                r.click(r.find(imgRoot + "AutoComplete.png"));
                heroUpgrade();
            }
        } else{
        print("no more Quests to commplete");
        }
    }
    public void epicQuestStart()throws Exception {
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)&&(doesItExist(imgRoot + "EpicQuest.png", .94, r))) {
                while (doesItExist(imgRoot + "EpicQuest.png", .94, r)&&(!doesItExist(imgRoot + "quest_running.png",.80,r))) {
                    print("Epic quests");
                    r.click(r.find(imgRoot + "EpicQuest.png").getBottomRight());
                    heroUpgrade();
                }
            } else {
                print("no Epic Quests");
            }
    }
    public void legendaryQuestStart()throws Exception {
       /* try{
            ScreenRegion questButton = app.find(new ImageTarget(new File(imgRoot+"LegendaryQuest.png")));
            ScreenLocation questButtonLocation = questButton.getLowerRightCorner();
            print("looking for Start Legendary Quests button");
            mouse.click( questButtonLocation );
            heroUpgrade();
            mouse.click( questButtonLocation );
            heroUpgrade();
        }*/
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)&&(doesItExist(imgRoot + "LegendaryQuest.png", .93, r))) {
                while (doesItExist(imgRoot + "LegendaryQuest.png", .93, r)&&(!doesItExist(imgRoot + "quest_running.png",.80,r))) {
                    print("Legendary quests");
                    r.click(r.find(imgRoot + "LegendaryQuest.png").getBottomRight());
                    heroUpgrade();
                }
            } else {
            print("no Legendary Quests");
        }
    }
    public void rareQuestStart() throws Exception{
        /*try{
            ScreenRegion questButton = app.find(new ImageTarget(new File(imgRoot+"RareQuest.png")));
            ScreenLocation questButtonLocation = questButton.getLowerRightCorner();
            print("looking for Start Rare Quests button");
            mouse.click( questButtonLocation );
            heroUpgrade();
            mouse.click( questButtonLocation );
            heroUpgrade();
        }   catch (Exception e) {
            print("cant find Rare Quests button");
        }*/
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)&&(doesItExist(imgRoot + "RareQuest.png", .93, r))) {
                while (doesItExist(imgRoot + "RareQuest.png", .93,r)&& (!doesItExist(imgRoot + "quest_running.png",.80,r))) {
                    print("Rare quests");
                    r.click(r.find(imgRoot + "RareQuest.png").getBottomRight());
                    heroUpgrade();
                }
            } else {
                print("no Rare Quests");
            }
    }
    public void commonQuestStart()throws Exception{
        /*try{
            ScreenRegion questButton = app.find(new ImageTarget(new File(imgRoot+"commonQuest.png")));
            ScreenLocation questButtonLocation = questButton.getLowerRightCorner();
            print("looking for Start Common Quests button");
            mouse.click( questButtonLocation );
            heroUpgrade();
            mouse.click( questButtonLocation );
            heroUpgrade();
        }   catch (Exception e) {
            print("cant find Common Quests button");
        }*/
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)&&(doesItExist(imgRoot + "commonQuest.png", .93, r))) {
            while (doesItExist(imgRoot + "commonQuest.png",.93,r)&&(!doesItExist(imgRoot + "quest_running.png",.80,r))) {
                print("Common quests");
                r.click(r.find(imgRoot + "commonQuest.png").getBottomRight());
                heroUpgrade();
            } }   else {
            print("no Common Quests");
        }
    }
    public void uncommonQuestStart() throws Exception{
        /*try{
            ScreenRegion questButton = app.find(new ImageTarget(new File(imgRoot+"UncommonQuest.png")));
            ScreenLocation questButtonLocation = questButton.getLowerRightCorner();
            print("looking for Start Uncommon Quests button");
            mouse.click( questButtonLocation );
            heroUpgrade();
            mouse.click( questButtonLocation );
            heroUpgrade();
        }   catch (Exception e) {
            print("cant find Uncommon Quests button");
        }*/
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)&&(doesItExist(imgRoot + "UncommonQuest.png", .93, r))) {
            while (doesItExist(imgRoot + "UncommonQuest.png",.93,r)&&(!doesItExist(imgRoot + "quest_running.png",.80,r))) {
                print("Uncommon quests");
                r.click(r.find(imgRoot + "UncommonQuest.png").getBottomRight());
                heroUpgrade();
            } }   else {
            print("no Uncommon Quests");
        }
    }
    public void basicQuestStart()throws Exception{
        /*try{
            ScreenRegion questButton = app.find(new ImageTarget(new File(imgRoot+"basicQuest.png")));
            ScreenLocation questButtonLocation = questButton.getLowerRightCorner();
            print("looking for Start Basic Quests button");
            mouse.click( questButtonLocation );
            heroUpgrade();
            mouse.click( questButtonLocation );
            heroUpgrade();
        }   catch (Exception e) {
            print("cant find Start Basic Quests button");
        }*/
        if(!doesItExist(imgRoot + "quest_running.png",.80,r)&&(doesItExist(imgRoot + "basicQuest.png", .80, r))) {
            while (doesItExist(imgRoot + "basicQuest.png",.80,r)&&(!doesItExist(imgRoot + "quest_running.png",.80,r))) {
                print("Basic quests");
                r.click(r.find(imgRoot + "basicQuest.png").getBottomRight());
                heroUpgrade();
            } }   else {
            print("no Basic Quests");
        }
    }
    public void heroUpgrade()throws Exception{
        if(doesItExist(imgRoot + "OKHero.png",.85,r)){
            while (doesItExist(imgRoot + "OKHero.png",.85,r)) {
                r.click(r.find(imgRoot + "OKHero.png"));
                Wait(1);
            }
        }
        else{print("no hero upgrade");}
    }

    public void startQuests()throws Exception{
        //Start new quest - look for Legendary,
        //Epic, Rare, uncommon, common, basic
        //rare
        if(!doesItExist(imgRoot + "noQuests.png",.85,r)) {
            legendaryQuestStart();
            epicQuestStart();
            rareQuestStart();
            uncommonQuestStart();
            commonQuestStart();
            basicQuestStart();
        }
        else print("No new quests. Come back later");
    }

    public void helps() throws Exception {
        if (doesItExist(imgRoot + "help.png",.85,r)) {
            r.hover(imgRoot + "help.png");
            r.click(r.find(imgRoot + "help.png"));
            print("tried to click Help");
            Wait(1);
            if (doesItExist(imgRoot + "help.png",.85,r)) {
                r.hover(imgRoot + "help.png");
                r.click(r.find(imgRoot + "help.png"));
                print("tried to click Help");
                Wait(1);
            }
        }
        if (doesItExist(imgRoot + "HelpAll.png",.80,r)) {
            r.click(r.find(imgRoot + "HelpAll.png"));
            print("clicked HelpAll");
        }
    }
    public void swipeDown()throws Exception{
        if (!doesItExist(imgRoot + "splash_gone.png",0.85,r)){
            townView();
        }
        mouse.hover(top);
        mouse.press();
        mouse.drag(top);
        mouse.drop(bottom);
        mouse.release();
        Wait(1);
    }
    public void swipeUp()throws Exception{
        if (!doesItExist(imgRoot + "splash_gone.png",0.85,r)){
            townView();
        }
        mouse.hover(bottom);
        mouse.press();
        mouse.drag(bottom);
        mouse.drop(top);
        mouse.release();
        Wait(1);
    }
    public void swipeLeft()throws Exception{
        if (!doesItExist(imgRoot + "splash_gone.png",0.85,r)){
            townView();
        }
        mouse.hover(goRight);
        mouse.press();
        mouse.drag(goRight);
        mouse.drop(goLeft);
        mouse.release();
        Wait(1);
    }
    public void swipeRight()throws Exception{
        if (!doesItExist(imgRoot + "splash_gone.png",0.85,r)){
            townView();
        }
        mouse.hover(goLeft);
        mouse.press();
        mouse.drag(goLeft);
        mouse.drop(goRight);
        mouse.release();
        Wait(1);
    }
    public boolean buildInProgress()throws Exception{
        if (doesItExist(imgRoot + "hammer.png",0.85,r)) {
            return true;
        }
        else if (!doesItExist(imgRoot + "splash_gone.png",0.85,r)){
            townView();
            }
        else if(doesItExist(imgRoot + "more.png",0.85,r)) {
            r.click(r.find(imgRoot + "more.png"));
            Wait(1);
            if (doesItExist(imgRoot + "more.png", 0.85, r)) {
                r.click(r.find(imgRoot + "more.png"));
                Wait(1);
            }
        }
        return (doesItExist(imgRoot + "hammer.png",0.85,r));
    }
    public void buildMe()throws Exception{
        if(!buildInProgress()) {
            buildMeRss1();
        if(!buildInProgress()) {
            buildMeRss2();
        if(!buildInProgress()) {
            buildMeFrontGate();
        if(!buildInProgress()) {
            buildMeRightTown();
        if(!buildInProgress()) {
            buildMeMidTown();
        if(!buildInProgress()) {
            buildMeLeftTown();
        if(!buildInProgress()) {
            buildMeLowerLeftTown();
            }}}}}}
        }
        else{
            print("Build is in progress");
        }
    }
    //farthest rss to the right
    public boolean buildMeRss1() throws Exception {
        return findBuild();
    }
    //next after rss1
    public boolean buildMeRss2() throws Exception {
        swipeLeft();
        return findBuild();
    }
    public boolean buildMeFrontGate() throws Exception {
        swipeLeft();
        return findBuild();
    }
    public boolean buildMeRightTown() throws Exception {
        swipeLeft();
        swipeLeft();
        return findBuild();
    }
    public boolean buildMeMidTown() throws Exception {
        swipeUp();
        return findBuild();
    }
    public boolean buildMeLeftTown() throws Exception {
        swipeRight();
        return findBuild();
    }
    public boolean buildMeLowerLeftTown() throws Exception {
        swipeRight();
        return findBuild();
    }


    public String howMuchRss(String rss)throws ParseException{
        Settings.OcrTextSearch = true;
        Settings.OcrTextRead = true;
        Region rssRegion = null;
        if(rss.equals("stone")){
            rssRegion = new Region(1025,106,27,10);//stone
        }
        if(rss.equals("wood")){
            rssRegion = new Region(1085,105,27,11);//wood
            rssRegion.hover();
            Wait(3);
             }
        if(rss.equals("ore")){
            rssRegion = new Region(1146,106,30,10);//ore
        }
        if(rss.equals("food")){
            rssRegion = new Region(1208,106,23,10);//food
        }
        if(rss.equals("silver")){
            rssRegion = new Region(1271,106,26,10);//silver
        }
        print("rss" + rssRegion.text());

        double Qty = (NumberFormat.getInstance().parse(rssRegion.text())).intValue();
        print(rss+" " + Qty);
        String amount = ""+Qty+" "+getGross(rssRegion.text());
        print(amount);
        return amount;
    }
    public Character getGross(String gross) {
        char letter = ' ';    // <-- initialize

        for (char c : gross.toCharArray()) {
            if (Character.isLetter(c)) {      // if the character is a letter, save if the "letter"
                letter = c;
                break;
            }
        }
        return letter;
    }

    public boolean findBuild()throws Exception{
        print("started");
        if (doesItExist(imgRoot + "build_wood_lower.png", 0.90, build)) {
            Integer buildItX = build.find(imgRoot + "build_wood_lower.png").getX();
            Integer buildItY = build.find(imgRoot + "build_wood_lower.png").getY();
            ScreenLocation buildme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(-20,20);
            mouse.hover(buildme);
            Wait(1);
            mouse.click(buildme);
            upgradeMe();
            print("Was a build found? True");
            return true;
        }
        else if (doesItExist(imgRoot + "build_wood_new.png", 0.95, build)) {
            Integer buildItX = build.find(imgRoot + "build_wood_new.png").getX();
            Integer buildItY = build.find(imgRoot + "build_wood_new.png").getY();
            ScreenLocation buildme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(-20,20);
            mouse.hover(buildme);
            Wait(1);
            mouse.click(buildme);
            upgradeMe();
            print("Was a build found? True");
            return true;
        }
/*        else if (doesItExist(imgRoot + "build_wood_another.png", 0.84, build)) {
            Integer buildItX = build.find(imgRoot + "build_wood_another.png").getX();
            Integer buildItY = build.find(imgRoot + "build_wood_another.png").getY();
            ScreenLocation buildme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(-20,20);
            mouse.hover(buildme);
            Wait(1);
            mouse.click(buildme);
            upgradeMe();
            return true;
        }*/
        else {print("No Build found");
            return false;
        }
    }
    public void upgradeMe()throws Exception{
        Wait(2);
        try {
            if (doesItExist(imgRoot + "BadBuildMove.png", 0.85, r)) {
                r.click(r.find(imgRoot + "BadBuildMove.png").getCenter());
                swipeDown();
                //assert true;
            } else if (doesItExist(imgRoot + "upgrade1.png", 0.85, r)) {
                if (doesItExist(imgRoot + "upgrade1.png", 0.85, r)) {
                    r.click(r.find(imgRoot + "upgrade1.png").getCenter());
                    Wait(1);
                    checkDontBuild();
                }
                if (!doesItExist(imgRoot + "upgrade2.png", 0.85, r)) {
                    r.click(r.find(imgRoot + "upgrade1.png").getCenter());
                    checkDontBuild();
                }
                if (doesItExist(imgRoot + "upgrade2.png", 0.85, r)) {
                    r.click(r.find(imgRoot + "upgrade2.png").getCenter());
                }
                if (!doesItExist(imgRoot + "getHelp.png", 0.85, r) && doesItExist(imgRoot + "upgrade2.png", 0.85, r)) {
                    r.click(r.find(imgRoot + "upgrade2.png").getCenter());
                }
                if (doesItExist(imgRoot + "getHelp.png", 0.85, r)) {
                    r.click(r.find(imgRoot + "getHelp.png").getTopLeft());
                }
                Wait(1);
                if (doesItExist(imgRoot + "GoldX.png", 0.85, r)) {
                    r.click(r.find(imgRoot + "GoldX.png"));
                }
                Wait(1);
            }
        }catch(Exception e){print("blah");}
    }

    public void checkDontBuild()throws Exception{
        if(doesItExist(imgRoot + "dont_build.png",0.85,r)||doesItExist(imgRoot + "dont_build3.png",0.85,r)){
            townView();
            swipeUp();
            assert true;
        }
        else if (doesItExist(imgRoot + "dont_build2.png",0.85,r)){
            r.click(r.find(imgRoot + "dont_build2.png").getCenter());
            swipeDown();
            assert true;
        }
        if(doesItExist(imgRoot + "alliance_chat.png",0.85,r)){
            r.click(r.find(imgRoot + "chat_x.png").getCenter());
            if(doesItExist(imgRoot + "chat_x.png",0.85,r)){
                r.click(r.find(imgRoot + "chat_x.png").getCenter());
            }
        }
    }
    public void stopRally() throws Exception {
        //To stop a rally
        r.exists(imgRoot + "march.png").click(imgRoot + "march.png");
        //cancel
        r.click(imgRoot + "cancelRally.png");
        //confirm
        r.click(imgRoot + "confirm.png");
    }

    //to set a rally - TO DO
    //click on bookmarks
    //r.click(imgRoot+"1404084281316.png");

    public void rssSendHelpButton() throws Exception {
        //Click help to send
        try {
            Wait(1);
            while(doesItExist(imgRoot + "rssHelp.png",.85,r)){
                r.click(r.find(imgRoot + "rssHelp.png"));
            }
            while(doesItExist(imgRoot + "rssHelpTimer.png",.85,r)){
                r.click(r.find(imgRoot + "rssHelpTimer.png"));
            }
            /*r.click(r.find(imgRoot + "rssHelp.png"));
            r.click(r.find(imgRoot + "rssHelp.png"));*/
            print("clicked Help");
        } catch (Exception e) {
            r.click(r.find(imgRoot + "rssHelpTimer.png"));
            print("for some reason there was no help");
        }
    }
    public void rssSendOKButton() throws Exception {
        //ScreenRegion okPresent = app.find(new ImageTarget(new File(imgRoot+"rssOK.png")));
        try {
            Wait(1);
            if(doesItExist(imgRoot + "rssOK.png",.85,r)){
                r.click(r.find(imgRoot + "rssOK.png"));
                Wait(1);
            }
            print("clicked OK");
            Wait(1);
            if(doesItExist(imgRoot + "rssOK.png",.85,r)){
                r.click(r.find(imgRoot + "rssOK.png"));
            }
            Wait(1);
            if(doesItExist(imgRoot + "rssOK.png",.85,r)){
                r.click(r.find(imgRoot + "rssOK.png"));
            }
        }catch (Exception e){
            print("for some reason there was no OK");
        }
    }

    public void clickMagGlass()throws Exception{
        if (doesItExist(imgRoot + "GoldX.png", 0.85, r)) {
            r.click(r.find(imgRoot + "GoldX.png"));
        }
        doesItExist(imgRoot +"magGlass.png",0.82,r);
        for (int i = 0; i < 5; i++) {
        if(!doesItExist(imgRoot +"magGlass.png",0.82,r)) {
            kingdomView();
            Wait(1);
            print("waiting 1 sec");
            //  doesItExist(imgRoot +"gotoCoords.png",0.82,r);
        }
            if (doesItExist(imgRoot +"magGlass.png",0.82,r)){
                break;
             }
            else{
                if (doesItExist(imgRoot + "MZLogo.png", .85, r)) {
                    r.click(r.find(imgRoot + "MZLogo.png"));
                    print("clicked MZLogo/accounts");
                    Wait(1);
                }
            }
        }
        if(doesItExist(imgRoot+"magGlass.png",0.82,r)){
            Integer buildItX = r.find(imgRoot + "magGlass.png").getX();
            Integer buildItY = r.find(imgRoot + "magGlass.png").getY();
            ScreenLocation magGlass = new DesktopScreenLocation(buildItX,buildItY);
            mouse.hover(magGlass);
            mouse.doubleClick(magGlass);
            Wait(1);
            mouse.doubleClick(magGlass);
        }
        Wait(1);
        print("clicked on mag glass");
    }
    @Test
    public void enterCoords (String XCoord,String YCoord)throws Exception{
        Wait(1);
        if(doesItExist(imgRoot+"goToX.png",0.82,r)){
           // Integer buildItX = r.find(imgRoot + "goToX.png").getX();
            //Integer buildItY = r.find(imgRoot + "goToX.png").getY();
          //  print("X: "+buildItX +" Y: "+buildItY);
            ScreenLocation typeXCoord = new DesktopScreenLocation(1118,211).getRelativeScreenLocation(30,20);
            mouse.hover(typeXCoord);
            Wait(1);
            mouse.doubleClick(typeXCoord);
            mouse.doubleClick(typeXCoord);
            Wait(2);
            kb.type(Key.BACKSPACE);
            kb.type(Key.BACKSPACE);
            kb.type(Key.BACKSPACE);
            kb.type(XCoord);
            kb.type(Key.ENTER);
            //82r.click(r.find(imgRoot + "goToX.png"));
            print("XCoord typed: "+XCoord);
        }
        if(doesItExist(imgRoot+"goToY.png",0.82,r)){
            Integer buildItX = r.find(imgRoot + "goToY.png").getX();
            Integer buildItY = r.find(imgRoot + "goToY.png").getY();
            print("X: "+buildItX +" Y: "+buildItY);
            ScreenLocation typeYCoord = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(30,20);
            mouse.hover(typeYCoord);
            mouse.doubleClick(typeYCoord);
            mouse.doubleClick(typeYCoord);
            kb.type(Key.BACKSPACE);
            kb.type(Key.BACKSPACE);
            kb.type(Key.BACKSPACE);
            Wait(2);
            kb.type(YCoord);
            kb.type(Key.ENTER);
            print("YCoord typed: "+YCoord);
        }
    }
    public void gotoCoord()throws Exception {
        if(doesItExist(imgRoot+"gotoCoords.png",0.82,r)){
            r.click(r.find(imgRoot + "gotoCoords.png"));
        }
    }
    public void findBankCoords(String XCoord,String YCoord)throws Exception {
        clickMagGlass();
        Wait(1);
        enterCoords(XCoord,YCoord);
        Wait(1);
        gotoCoord();
        Wait(1);
        if(doesItExist(imgRoot+"validCoords.png",0.82,r)){
            enterCoords(XCoord,YCoord);
            Wait(1);
            gotoCoord();
            Wait(1);
        }
    }
    public void bookmarks() throws Exception {
        Wait(1);
        doesItExist(imgRoot +"bookmarks.png",0.82,r);
        //print("1. Is the bookmark button there? " + doesItExist(imgRoot +"bookmarks.png",0.85));
        while(!doesItExist(imgRoot +"bookmarks.png",0.82,r)) {
            kingdomView();
            Wait(1);
            print("waiting");
            doesItExist(imgRoot +"bookmarks.png",0.82,r);
            /*print("1.5 Is the bookmark button there? " + doesItExist(imgRoot +"bookmarks.png",0.85));*/
        }
        doesItExist(imgRoot +"bookmarks.png",0.82,r);
        if (doesItExist(imgRoot +"bookmarks.png",0.82,r)) {
            Wait(1);
            ScreenLocation e = Relative.to(bookmarkButton).right(90).below(12).getScreenLocation();
            mouse.doubleClick(e);
            print("clicked on bookmark button");
            doesItExist(imgRoot +"bookmarks.png",0.82,r);
        }
    }
    public void clickBookmarksFavorite() throws Exception {
        try {
            if (doesItExist(imgRoot + "favorite.png",.80,r)){
                r.click(r.find(imgRoot + "favorite.png"));
            }
        }catch(Exception e){
            print("Cant find favorite");
        }
    }
    public void clickBookmarksEnemy() throws Exception {
        try {
            if (doesItExist(imgRoot + "enemy.png",.80,r)){
                r.click(r.find(imgRoot + "enemy.png"));
            }
        }catch(Exception e) {
            print("Cant find enemy");
        }
    }
    public void clickBookmarksFriend() throws Exception {
        try {
            if (doesItExist(imgRoot + "friend.png",.80,r)){
                r.click(r.find(imgRoot + "friend.png"));
            }
        }catch(Exception e) {
            print("Cant find friend");
        }
    }
    public void clickBookmarksGoTo() throws Exception {
        Wait(1);
            if (doesItExist(imgRoot + "GoTo.png",.80,r)){
                r.click(r.find(imgRoot + "GoTo.png"));
            }
            Wait(2);
            if (doesItExist(imgRoot + "GoTo.png",.80,r)){
                r.click(r.find(imgRoot + "GoTo.png"));
            }
    }
    public void findBank() throws Exception {
        //by bookmarks in friends
        kingdomView();
        bookmarks();
        clickBookmarksFavorite();
        clickBookmarksEnemy();
        clickBookmarksGoTo();
    }
    public void sendHelpOK() throws Exception{
        rssSendHelpButton();
        rssSendOKButton();
    }

    public void sendSilverPlus(String rss) throws Exception {
        if (rss.equals("ore")) {//send ore
            sendOre();
            sendSilver();
        }
        if (rss.equals("stone")) {//send Stone
            sendStone();
            sendSilver();
        }
        if (rss.equals("wood")) {//send wood
            sendWood();
            sendSilver();
        }
        if (rss.equals("food")) {//send Food
            sendFood();
            sendSilver();
        }
        if (rss.equals("silver")) {//send Food
            sendSilver();
        }
        if (rss.equals("")) {//send only silver
            sendSilver();
        }
        sendHelpOK();
    }

    public void sendStone() throws Exception {
        Wait(1);
        mouse.hover(stoneBeg);
        //mouse.hover(stoneEnd);
        mouse.press();
        mouse.drag(stoneBeg);
        mouse.drop(stoneEnd);
        //r.dragDrop(imgRoot + "Stone.png", imgRoot + "stoneEND.png");
    }

    public void sendWood() throws Exception {
        Wait(1);
        mouse.hover(woodBeg);
        //mouse.hover(woodEnd);
        mouse.press();
        mouse.drag(woodBeg);
        mouse.drop(woodEnd);
        //r.dragDrop(imgRoot + "Wood.png", imgRoot + "woodEND.png");
    }

    public void sendFood() throws Exception {
        Wait(1);
        mouse.hover(foodBeg);
        //mouse.hover(foodEnd);
        mouse.press();
        mouse.drag(foodBeg);
        mouse.drop(foodEnd);
        //r.dragDrop(imgRoot + "Food.png", imgRoot + "foodEND.png");
    }

    public void sendOre() throws Exception {
        Wait(1);
        mouse.hover(oreBeg);
        //mouse.hover(oreEnd);
        mouse.press();
        mouse.drag(oreBeg);
        mouse.drop(oreEnd);
        //r.dragDrop(imgRoot + "Ore.png", imgRoot + "oreEND.png");
    }

    public void sendSilver() throws Exception {
        //print("not sending silver for a bit.");
        Wait(1);
        mouse.hover(silverBeg);
        //mouse.hover(silverEnd);
        mouse.press();
        mouse.drag(silverBeg);
        mouse.drop(silverEnd);
       // r.dragDrop(imgRoot + "Silver.png", imgRoot + "silverEND.png");
    }

    public void clickBank() throws Exception {
        //click on bank
        Wait(2);
        ScreenLocation e = Relative.to(bank).right(33).below(55).getScreenLocation();
        mouse.click(e);
        mouse.click(e);
        Wait(1);
    }
    public void clickResourceHelp() throws Exception {
        //click resource help
        Wait(1);
        if (doesItExist(imgRoot + "ResourceHelp.png",.80,r)) {
            r.click(imgRoot + "ResourceHelp.png");
        }
    }

    public void sendToBank(Integer marchLength, String rss, Integer trip) throws Exception {
        try {
            if (trip != 0) {
                clickBank();
                clickResourceHelp();
                //always send silver
                sendSilverPlus(rss);
                //then send 1+ more load of primary rss
                if (!rss.equals("")) {
                    for (int i = 0; i < trip; i++) {
                        //Wait(marchLength);
                        Wait(5);
                        clickBank();
                        clickResourceHelp();
                        if (rss.equals("ore")) {//send ore
                            print("sending "+rss);
                            sendSilverPlus(rss);
                        }
                        if (rss.equals("stone")) {//send Stone
                            print("sending "+rss);
                            sendSilverPlus(rss);
                        }
                        if (rss.equals("wood")) {//send wood
                            print("sending "+rss);
                            sendSilverPlus(rss);
                        }
                        if (rss.equals("food")) {//send Food
                            print("sending "+rss);
                            sendSilverPlus(rss);
                        }
                        if (rss.equals("silver")) {//send ore
                            print("sending "+rss);
                            sendSilver();
                        }
                        sendHelpOK();
                    }
                }
            } else {
                print("don't send rss for now.");
            }
        }
        catch (Exception e){
            print("failed to get to bank");
        }
    }
@Test
    public void closeGOW() throws Exception {
        Wait(15);
        if (doesItExist(imgRoot + "more.png", .75, r)) {
            if (doesItExist(imgRoot + "closeGOW.png", .80, r)) {
                r.hover(r.find(imgRoot + "closeGOW.png"));
                r.click(r.find(imgRoot + "closeGOW.png"));
                //not sure why this is there. seeing if i really need it.
            /*while(!doesItExist(imgRoot + "NoquitGame.png",.80,r)) {
                r.hover(r.find(imgRoot + "closeGOW.png"));
                r.click(r.find(imgRoot + "closeGOW.png"));
                Wait(3);
            }*/
                print("clicked back on GOW");
            }
        }
        else{
            Wait(10);
            if (doesItExist(imgRoot + "closeGOW.png", .80, r)) {
                r.hover(r.find(imgRoot + "closeGOW.png"));
                r.click(r.find(imgRoot + "closeGOW.png"));
                //not sure why this is there. seeing if i really need it.
            /*while(!doesItExist(imgRoot + "NoquitGame.png",.80,r)) {
                r.hover(r.find(imgRoot + "closeGOW.png"));
                r.click(r.find(imgRoot + "closeGOW.png"));
                Wait(3);
            }*/
                print("clicked back on GOW");
            }
        }
        if (!doesItExist(imgRoot + "aboutGOW.png", .80, r)) {
            r.hover(r.find(imgRoot + "closeGOW.png"));
            r.click(r.find(imgRoot + "closeGOW.png"));
            /*while(!doesItExist(imgRoot + "NoquitGame.png",.80,r)) {
                r.hover(r.find(imgRoot + "closeGOW.png"));
                r.click(r.find(imgRoot + "closeGOW.png"));
                Wait(1);
            }*/
            print("clicked back on GOW");
        }
    }
    public void dontQuitGOW() throws Exception{
        if(doesItExist(imgRoot + "NoquitGame.png",.80,r)){
            r.click(r.find(imgRoot + "NoquitGame.png"));
            print("Do not quit GOW");
        }
    }
    public void quitGOW() throws Exception{
        if(doesItExist(imgRoot + "quitGame.png",.80,r)){
            r.click(r.find(imgRoot + "quitGame.png"));
            print("quit GOW");
        }
        else closeBlueStacks();
    }
    public void closeBlueStacks() throws Exception{
        r.click(r.find(imgRoot + "BlueStacksX.png"));
        print("clicked x on the BlueStacks main window");
    }
    public void killBlueStacks() throws Exception{
        String[] envar = {"VAR=path"};
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\BlueStacks\\HD-Quit.exe", envar);
        print("made BlueStacks Quit");
    }
    public void exitGOW() throws Exception {

        if (doesItExist(imgRoot + "aboutGOW.png", .85, r)) {
            r.hover(r.find(imgRoot + "aboutGOW.png"));
            Wait(1);
            r.doubleClick(r.find(imgRoot + "aboutGOW.png"));
            if (doesItExist(imgRoot + "aboutGOW.png", .85, r)) {
                r.hover(r.find(imgRoot + "aboutGOW.png"));
                Wait(1);
                r.doubleClick(r.find(imgRoot + "aboutGOW.png"));
                print("clicked + in menu");
                Wait(1);
                if (doesItExist(imgRoot + "buildingSwap.png", .85, r)) {
                    swipeDown();
                }
                if (doesItExist(imgRoot + "MZLogo.png", .85, r)) {
                    r.click(r.find(imgRoot + "MZLogo.png"));
                    print("clicked MZLogo/accounts");
                    Wait(1);
                    if (doesItExist(imgRoot + "MZLogo.png", .85, r)) {
                        r.click(r.find(imgRoot + "MZLogo.png"));
                        print("clicked MZLogo/accounts");
                        Wait(1);
                    }
                }
                Wait(5);
                if (doesItExist(imgRoot + "LogOut.png", .72, r)) {
                    r.click(r.find(imgRoot + "LogOut.png"));
                    Wait(2);
                    if (doesItExist(imgRoot + "LogOut.png", .72, r)) {
                        r.click(r.find(imgRoot + "LogOut.png"));
                    }
                    Wait(2);
                    if (doesItExist(imgRoot + "LogOut.png", .72, r)) {
                        r.click(r.find(imgRoot + "LogOut.png"));
                    }
                }
                if (!doesItExist(imgRoot + "LogoutYes.png", .75, r)) {
                    r.click(r.find(imgRoot + "LogOut.png"));
                }
                print("clicked Log Out");
                Wait(3);
                if (doesItExist(imgRoot + "LogoutYes.png", .80, r)) {
                    r.click(r.find(imgRoot + "LogoutYes.png"));
                    Wait(3);
                    if (doesItExist(imgRoot + "LogoutYes.png", .80, r)) {
                        r.click(r.find(imgRoot + "LogoutYes.png"));
                    }
                    Wait(1);
                }
                print("clicked Yes Log out");
                Wait(5);
            /*if(doesItExist(imgRoot + "enter_email.png",.90,r)){
                closeGOW();
                quitGOW();
            }*/

       /* else {
            closeGOW();
            quitGOW();
            print("running after test");
            startup();
            closeGOW();
            dontQuitGOW();
            exitGOW();
        }*/
            } /*else if (doesItExist(imgRoot + "1408112219147.png", .90, fullScreen)) {
                fullScreen.click(fullScreen.find(imgRoot + "1408112219147.png"));
            }*/
        }
    }
    //String subject, String message
    public void email(String login, String emailFrom, String emailTo, String password)throws Exception{
        String subject = login + " just started";
        String message = login + " has begun running. ";
        sendMeAnEmail(subject, message, emailFrom, emailTo, password);
    }
    public void sendMeAnEmail(String subject, String message,String emailFrom, String emailTo, String password)throws Exception{
        Properties emailProperties;
        Session mailSession;
        MimeMessage emailMessage;
        //JavaEmail javaEmail = new JavaEmail();

        //javaEmail.setMailServerProperties();
        String emailPort = "587";//gmail's smtp port
        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

        //javaEmail.createEmailMessage();
        String[] toEmails = { emailTo };
        String emailSubject = subject;
        String emailBody = message;

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");//for a html email
        //emailMessage.setText(emailBody);// for a text email

       // javaEmail.sendEmail();
        String emailHost = "smtp.gmail.com";
        String fromUser = emailFrom;//just the id alone without @gmail.com
        String fromUserEmailPassword = password;

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }

    public void collectRss(String rss)throws Exception{
        while (doesItExist(imgRoot + "use_silver.png", 0.95, r)) {
            Integer buildItX = r.find(imgRoot + "use_silver.png").getX();
            Integer buildItY = r.find(imgRoot + "use_silver.png").getY();
            ScreenLocation useme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(340,-12);
            mouse.hover(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
        }
        while (doesItExist(imgRoot + "use_ore.png", 0.90, r)) {
            Integer buildItX = r.find(imgRoot + "use_ore.png").getX();
            Integer buildItY = r.find(imgRoot + "use_ore.png").getY();
            ScreenLocation useme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(340,-12);
            mouse.hover(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
        }
        while (doesItExist(imgRoot + "use_stone.png", 0.90, r)) {
            Integer buildItX = r.find(imgRoot + "use_stone.png").getX();
            Integer buildItY = r.find(imgRoot + "use_stone.png").getY();
            ScreenLocation useme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(340,-12);
            mouse.hover(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
        }
        while (doesItExist(imgRoot + "use_wood.png", 0.90, r)) {
            Integer buildItX = r.find(imgRoot + "use_wood.png").getX();
            Integer buildItY = r.find(imgRoot + "use_wood.png").getY();
            ScreenLocation useme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(340,-12);
            mouse.hover(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
        }
        /*while (doesItExist(imgRoot + "use_food.png", 0.90, r)) {
            Integer buildItX = r.find(imgRoot + "use_food.png").getX();
            Integer buildItY = r.find(imgRoot + "use_food.png").getY();
            ScreenLocation useme = new DesktopScreenLocation(buildItX,buildItY).getRelativeScreenLocation(340,-12);
            mouse.hover(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
            mouse.doubleClick(useme);
        }*/
    }

}
