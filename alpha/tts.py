import subprocess
from pathlib import Path
import json


class SomaliTTS:
    def __init__(self, config_path="data/config.json"):
        self.config_path = Path(config_path)
        self.config = self._load_config()

    def _load_config(self):
        try:
            return json.loads(self.config_path.read_text())
        except Exception:
            return {
                "language": "so",
                "tts_enabled": True,
                "tts_language": "so",
                "tts_rate": 0.9,
                "tts_pitch": 1.0
            }

    def speak(self, text):
        if not text:
            return False

        if not self.config.get("tts_enabled", True):
            return False

        language = self.config.get("tts_language", "so")
        rate = str(self.config.get("tts_rate", 0.9))
        pitch = str(self.config.get("tts_pitch", 1.0))

        try:
            result = subprocess.run(
                [
                    "termux-tts-speak",
                    "-l", language,
                    "-r", rate,
                    "-p", pitch,
                    text
                ],
                capture_output=True,
                text=True
            )

            return result.returncode == 0

        except FileNotFoundError:
            return False


if __name__ == "__main__":
    tts = SomaliTTS()
    ok = tts.speak(
        "Salaan. Waxaan ahay Alpha, kaaliyahaaga casriga ah."
    )

    print("TTS:", "OK" if ok else "FAILED")
