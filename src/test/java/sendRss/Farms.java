package sendRss;

import base.Base;

import org.junit.Before;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.annotations.*;


public class Farms extends Base {
    @DataProvider
    public Object[][] Mains() {
        return new Object[][]{//set trip to 0 if you want nothing send
                //NOT USED - integer - the number of seconds the round trip is for sending rss.
                //integer, trip is number of trips -1 that the account can make in the example the farm has 2 free marches (1 march may be rallied or camped)
                //NOT USED - string - the type of account. Idea behind this was different actions depending the type of account.
                //string - rss to be sent.
                //NOT USED - string - kingdom. idea was same as type of account.
                //string - email address.
                //string - password
                //string - x coord of place you want to send to (or attack, or rally for that matter. but only send is set up.
                //string - y coord
                //string - look for build (1) or not (0)
                //string - email address from send message from do not include the @ and after. it is just the beginning.
                //string - email address to send the message to
                //string - email password to send the message
                {20, 1, "farm", "stone", "82", "emial account", "password", "16", "670","0","xxx","xxx@gmail.com","password"},//notes
        };
    }
    @DataProvider
    public Object[][] FakeRally() {
        return new Object[][]{
                {"email account", "password","emailFrom","emailTo","emailPassword"},//
        };
    }

    @DataProvider
    public Object[][] attackIt() {
        return new Object[][]{
                {"email account", "password", "number of troops to send","emailFrom","emailTo","emailPassword"},//this should be changed to accept a target.
        };
    }

    @Test(dataProvider = "Mains", timeOut = 800000)
    public void woodFarm(Integer marchLength, Integer trip, String type, String rss, String kingdom, String login,
                         String password, String XCoord, String YCoord, String build, String emailFrom, String emailTo, String emailPassword) throws Exception {
        startGOW(login, password, emailFrom, emailTo, emailPassword);
        closeGOW();
        dontQuitGOW();
        treasureChest();
        closeGOW();
        dontQuitGOW();
        athenaTreasureChest();
        closeGOW();
        dontQuitGOW();
        helps();
        allianceGiftsButton();
        freeSpin();
        doQuests();
        if (build.equals("1")) {
          buildMe();
         }
        if (trip != 0) {
            findBankCoords(XCoord, YCoord);
            sendToBank(marchLength, rss, trip);
        }
        helps();
        exitGOW();
        Wait(2);
    }

    @Test(dataProvider = "FakeRally", timeOut = 600000)
    public void rallyTest(String login, String password, String emailFrom, String emailTo, String emailPassword) throws Exception {
        startGOW(login, password, emailFrom, emailTo, emailPassword);
        closeGOW();
        dontQuitGOW();
        treasureChest();
        athenaTreasureChest();
        helps();
        allianceGiftsButton();
        freeSpin();
        doQuests();
        buildMe();
        helps();
        exitGOW();
        Wait(2);
    }

    @Test(dataProvider = "attackIt", timeOut = 600000)//troops is number of troops to send
    public void attackIt(String login, String password, String troops, String emailFrom, String emailTo, String emailPassword) throws Exception {
        startGOW(login, password, emailFrom, emailTo, emailPassword);
        closeGOW();
        dontQuitGOW();
        treasureChest();
        athenaTreasureChest();
        attack(troops);
        helps();
        allianceGiftsButton();
        //freeSpin();
        doQuests();
        buildMe();
        helps();
        exitGOW();
        Wait(2);
    }
    @Test
    public void killBS()throws Exception{
        killBlueStacks();
        Wait(60);
        launchBS();
        Wait(100);
    }
    @Test
    public void killBS2()throws Exception{
        killBlueStacks();
        Wait(60);
        launchBS();
        Wait(100);
    }
    @Test
    public void killBS3()throws Exception{
        killBlueStacks();
        Wait(60);
        launchBS();
        Wait(100);
    }
    @Test
    public void killBS4()throws Exception{
        killBlueStacks();
        Wait(60);
        launchBS();
        Wait(100);
    }
    @Test
    public void killBS5()throws Exception{
        killBlueStacks();
        Wait(60);
        launchBS();
        Wait(100);
    }
    @Test
         public void killBS6()throws Exception{
        killBlueStacks();
        Wait(60);
        launchBS();
        Wait(100);
    }
}