package INF3132.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import INF3132.parser.exception.InvalidMonsterTypeException;
import INF3132.monsters.*;

public abstract class BaseParser<T> {
    private final BufferedReader reader;

    public BaseParser(String path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path));
    }

    protected abstract T parseBlock(Map<String, String> blockData);

    /**
     * A function that reads one block and returns the data within it.
     * @param patternStart The pattern that defines where the block starts.
     * @param patternEnd the pattern that defines where the block ends.
     */
    protected Map<String, String> readBlock(String patternStart, String patternEnd) throws IOException {
        Map<String, String> blockData = new HashMap<>();
        String l;
        while((l = reader.readLine()) != null) {
            l = l.trim();

            if(l.equals(patternStart)) {
                while((l = reader.readLine()) != null) {
                    l = l.trim();
                    if(l.equals(patternEnd)) break;

                    List<String> values = new ArrayList<String>(
                        Arrays.asList(l.split("\\s+"))
                    );

                    values.removeIf(v -> v.isEmpty());

                    if (values.size() < 2) {
                        System.err.println("Ligne mal formée: " + l);
                        continue;
                    }

                    String key = values.get(0).trim();
                    String value = String.join(" ", values.subList(1, values.size())).trim();
                    blockData.put(key, value);
                }
                break;
            }
        }
        return blockData;
    }

    public static MonsterType parseMonsterType(String monsterTypeString) throws InvalidMonsterTypeException {
        String comp = monsterTypeString.toLowerCase();
        switch (comp) {
            case "electric":    return MonsterType.ELECTRIC;
            case "fire":        return MonsterType.FIRE;
            case "water":       return MonsterType.WATER;
            case "grass":       return MonsterType.GRASS;
            case "ground":      return MonsterType.GROUND;
            case "insect":      return MonsterType.INSECT;
            case "normal":      return MonsterType.NORMAL;
            default:            throw new InvalidMonsterTypeException();
        }
    }

    /**
     * A function that fully reads a file according to a start pattern and an end pattern. It then returns all the blocks of data that have been found.
     * @param patternStart The pattern that defines where the block starts.
     * @param patternEnd The pattern that defines where the block ends.
     */
    public List<T> parseFull(String patternStart, String patternEnd) throws IOException {
        List<T> result = new ArrayList<>();
        Map<String, String> blockData;
        while(!(blockData = readBlock(patternStart, patternEnd)).isEmpty()){
            result.add(parseBlock(blockData));
        }
        return result;
    }
}
