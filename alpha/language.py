import json
from pathlib import Path


class Language:
    def __init__(self, config_path="data/config.json"):
        self.config_path = Path(config_path)
        self.config = self._load()

    def _load(self):
        if not self.config_path.exists():
            return {
                "language": "so",
                "tts_enabled": True,
                "tts_language": "so",
                "tts_rate": 0.9,
                "tts_pitch": 1.0
            }

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

    @property
    def code(self):
        return self.config.get("language", "so")

    @property
    def tts_language(self):
        return self.config.get("tts_language", "so")

    @property
    def tts_enabled(self):
        return self.config.get("tts_enabled", True)

    def get(self, key, default=None):
        return self.config.get(key, default)


SO = {
    "welcome": "Ku soo dhawoow ALPHA OS.",
    "ready": "Alpha AI waa diyaar.",
    "help": "Kuwani waa amarada la heli karo:",
    "unknown": "Ma fahmin amarkaaga.",
    "goodbye": "Nabadgelyo. Alpha OS wuu xirmayaa.",
    "memory_saved": "Xogta waa la kaydiyay.",
    "memory_empty": "Xusuustu hadda way madhan tahay.",
    "security": "Nidaamka amniga waa shaqaynayaa.",
    "tts_ready": "Nidaamka codka Soomaaliga waa diyaar.",
    "error": "Qalad ayaa dhacay."
}


class Somali:
    def __init__(self):
        self.text = SO

    def get(self, key):
        return self.text.get(key, key)
