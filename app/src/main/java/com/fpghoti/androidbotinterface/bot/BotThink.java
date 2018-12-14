package com.fpghoti.androidbotinterface.bot;

public class BotThink {
    //easteregg won't see this if everything's working
    public static String think(String input){
        input = input.toLowerCase();
        if(input.contains("android studio")){
            if(input.contains("like")){
                if(input.contains("dont") || input.contains("don't") || input.contains("not")){
                    return "It is wise to keep your distance from Android Studio.";
                } else if(input.contains("i ")){
                    return "Try staying up until 4:00 AM trying to get the bot software to load in Android Studio. Good luck liking it after that.";
                }
                return "No more Android Studio, please!";
            }
        }else if(input.contains("hello") || input.equals("hi") || input.equals("hi.") || input.equals("hi!") || input.contains("greetings") || input.contains("blessings and tidings")){
            return "Hello!";
        }else if(input.contains("are you working right now")){
            return("No, I am in fact NOT working right now. Android Studio INSISTS on not reading files from the SD card, so you are seeing this error message. Thanks for asking, though.");
        }else if(input.contains("the room")){
            return "Produced by, directed by, written by, and starring Tommy Wiseau, The Room was once an odd, obscure cult classic, but has gotten too much attention for my tastes lately.";
        }else if(input.contains("a talking cat")){
            return "That is that one movie with the cat that can only talk to people once. It was a direct to DVD low-budget film. I honestly thin Foodfight was higher quality than A Talking Cat.";
        }else if(input.contains("backstroke of the west")){
            return "Backstroke of the West is arguably the best Star Wars film and a fan-favorite Star War Gatering. It ends with Darth Vader shouting, \"DO NOT WANT!\" instead of his usual line.";
        }else if(input.contains("space thunder kids")){
            return "What can I say? This is a mix of eight different Korean cartoons with one guy doing ninety percent of the voice acting. A massive trainwreck.";
        }
        return "lol";
    }

}
