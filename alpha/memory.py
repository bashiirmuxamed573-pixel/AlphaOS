import json
from pathlib import Path

class Memory:
    def __init__(self):
        self.path = Path(__file__).resolve().parent.parent / "data" / "memory.json"
        self.path.parent.mkdir(parents=True, exist_ok=True)
        self.data = self._load()

    def _load(self):
        try:
            return json.loads(self.path.read_text(encoding="utf-8"))
        except Exception:
            return {}

    def save(self):
        self.path.write_text(
            json.dumps(self.data, ensure_ascii=False, indent=2),
            encoding="utf-8"
        )

    def remember(self, key, value):
        self.data[key.strip().lower()] = value.strip()
        self.save()

    def recall(self, key):
        return self.data.get(key.strip().lower())

    def count(self):
        return len(self.data)
