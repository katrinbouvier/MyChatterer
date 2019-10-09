package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Chatterer {

    public String answer(String phrase, String userName) {
        Path path1 = Paths.get("./src/phrase_source/user_phrases.txt");
        Path path2 = Paths.get("./src/phrase_source/bot_phrases.txt");
        Path path3 = Paths.get("./src/phrase_source/answer_avoiding_phrases.txt");

        Map <String, String> userPatterns;
        Map <String, String> botPatterns;
        Map <String, Integer> answerAvoiding;

        userPatterns = getSet(path1); // пары K = V из файла
        botPatterns = getSet(path2); // пары V = K из файла(что?)
        answerAvoiding = getSet(path3);

        String answer = "Разработчик, обрати внимание...";
        boolean question = true;

        // фраза для сравнения с ключами
        String userWords =
                String.join(" ", phrase.toLowerCase().split("[ {,|.}?!]"));

        Optional<String> opt = userPatterns.entrySet().stream()
                .filter( k -> userWords.equals(k.getKey()))
                .map(Map.Entry::getValue)
                .map(botPatterns::get)
                .findFirst();

        if(opt.isPresent()) {
            return opt.get();
        }
        // если вопрос - избегаем
        else if (question && phrase.charAt(phrase.length()-1)=='?') {
            return getRandomPhrase(answerAvoiding);
        }
        //TODO: СДЕЛАТЬ НАБОР РАНДОМНЫХ ФРАЗ ПРОСТО ТАК
        return answer;
    }

    private <T, M> Map <T, M> getSet(Path path) {
        Map<T, M> set = new HashMap<>();
        List<String> lines;
        String[] arr;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                arr = lines.get(i).split("=");
                set.put((T)arr[0], (M)arr[1]);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return set;
    }

    private <T, M> String getRandomPhrase(Map<T, M> set) {
        int size = set.size();
        int rdm = 1 + (int) (Math.random() * size);
        int i = 1;

        for (Map.Entry<T, M> elem : set.entrySet()) {
            if (i == rdm) {
                return (String)elem.getValue();
            }
            i++;
        }
        return "";
    }

}