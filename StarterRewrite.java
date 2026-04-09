import io.github.NationArchitect.controller.savemanager.SaveData;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class StarterRewrite {
  public static void main(String[] args) throws Exception {
    Path path = Path.of(args[0]);
    String raw = Files.readString(path, StandardCharsets.UTF_8);
    if (!raw.isEmpty() && raw.charAt(0) == '\uFEFF') raw = raw.substring(1);
    SaveData data = SaveData.fromJson(raw);
    Files.writeString(path, data.toJson(), StandardCharsets.UTF_8);
  }
}
