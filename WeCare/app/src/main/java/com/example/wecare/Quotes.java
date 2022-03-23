package com.example.wecaremain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class Quotes {
    public static Map<String, ArrayList<String>> dict = Map.of(
            "dream", new ArrayList<String>() {
                {
                    add("Don't be pushed around by the fears in your mind. Be led by the dreams in your heart.");
                    add("Live the Life of Your Dreams: Be brave enough to live the life of your dreams according to your vision and purpose instead of the expectations and opinions of others.");
                    add("Do not fear failure but rather fear not trying.");
                    add("Surround Yourself with People Who Believe in Your Dreams:\n" +
                            "Surround yourself with people who believe in your dreams, encourage your ideas, support your ambitions, and bring out the best in you.");
                }},
                "inspiration", new ArrayList<String>() {
                {
                    add("Believe in yourself. You are braver than you think, more talented than you know, and capable of more than you imagine.");
                    add("It's not the load that breaks you down, it's the way you carry it.");
                    add("Success is not how high you have climbed, but how you make a positive difference to the world.");
                    add("Do you want to know who you are? Don't ask. Act! Action will delineate and define you.");
                    add("Nothing in the world is ever completely wrong. Even a stopped clock is right twice a day.");
                    add("Do not let the memories of your past limit the potential of your future. There are no limits to what you can achieve on your journey through life, except in your mind.");
                    add("The only thing standing between you and your goal is the bullshit story you keep telling yourself as to why you can't achieve it.");
                }
                },
            "grow", new ArrayList<String>() {
                {
                    add("It’s only after you’ve stepped outside your comfort zone that you begin to change, grow, and transform.");
                    add("Keep Going\n" +
                            "Your hardest times often lead to the greatest moments of your life. Keep going. Tough situations build strong people in the end.");
                    add("Life is about accepting the challenges along the way, choosing to keep moving forward, and savoring the journey.");
                    add("Never lose hope. Storms make people stronger and never last forever.”\n" +
                            "optimism:“Great things happen to those who don't stop believing, trying, learning, and being grateful.");
                    add("Let the improvement of yourself keep you so busy that you have no time to criticize others.");
                    add("Of course motivation is not permanent. But then, neither is bathing; but it is something you should do on a regular basis.");

                }
            },
            "love", new ArrayList<String>() {
                {
                    add("More smiling, less worrying. More compassion, less judgment. More blessed, less stressed. More love, less hate.");
                    add("Pursue what catches your heart, not what catches your eyes.");
                    add("Do what you love, love what you do, and with all your heart give yourself to it.");
                }
            },
            "else", new ArrayList<String>() {
                {
                    add("You never change your life until you step out of your comfort zone; change begins at the end of your comfort zone.");
                    add("The way to get started is to quit talking and begin doing.");
                    add("Believe in your infinite potential. Your only limitations are those you set upon yourself.");
                    add("Be Brave and Take Risks: You need to have faith in yourself. Be brave and take risks. You don't have to have it all figured out to move forward.");
                }
            }
    );
}
