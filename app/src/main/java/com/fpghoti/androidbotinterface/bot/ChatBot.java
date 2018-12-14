package com.fpghoti.androidbotinterface.bot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import com.jcabi.aspects.Async;

public class ChatBot {

    private Bot bot;
    private Chat chatSession = null;

    public ChatBot(String path) {
        bot = new Bot("default",path,"chat");
        chatSession = new Chat(bot);
    }

    @Async
    public String chatBot(String input) {
        String msg = "none";
        try {
            msg = chatSession.multisentenceRespond(input);
            //zelda cdi reference
            if(input.toLowerCase().contains("youve killed me") || input.toLowerCase().contains("you've killed me")){
                msg = "Good.";
            }
            int count = 0;

            //bot does not always respond
            while(msg.equalsIgnoreCase("") || msg.equalsIgnoreCase("I have no answer for that.")){
                String lastWord = input.substring(input.lastIndexOf(" ")+1);
                if(count > 0){
                    lastWord = "lol ";
                }
                msg = chatSession.multisentenceRespond(lastWord);
                if(count > 0){
                    msg = BotThink.think(input);
                }
                count++;
            }

            //bot sometimes tries to pull up weird link. I Decided to just disable this outright by replacing the message
            if(msg.contains("<oob>") || msg.contains("<a") || msg.toLowerCase().contains("unknown?")){
                msg = "If you say so.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String sendthis = msg;
        return sendthis;

    }

}
