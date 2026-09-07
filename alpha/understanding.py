class Understanding:

    def parse(self, text):

        raw = text.strip()
        low = raw.lower()

        if low in ("help", "commands"):
            return {"name": "help", "text": raw}

        if low in ("status", "xaalad"):
            return {"name": "status", "text": raw}

        if low in ("exit", "quit", "bye"):
            return {"name": "exit", "text": raw}

        for prefix in ("remember ", "xasuuso ", "save "):
            if low.startswith(prefix):
                return {
                    "name": "remember",
                    "text": raw[len(prefix):]
                }

        for prefix in (
            "recall ",
            "what is ",
            "what's ",
            "maxaa ah ",
            "maxay tahay "
        ):
            if low.startswith(prefix):
                return {
                    "name": "recall",
                    "text": raw[len(prefix):]
                }

        for prefix in ("open ", "fur "):
            if low.startswith(prefix):
                return {
                    "name": "open",
                    "text": raw[len(prefix):].strip()
                }

        for prefix in ("notify ", "ogeysiis "):
            if low.startswith(prefix):
                return {
                    "name": "notify",
                    "text": raw[len(prefix):].strip()
                }

        return {
            "name": "chat",
            "text": raw
        }
