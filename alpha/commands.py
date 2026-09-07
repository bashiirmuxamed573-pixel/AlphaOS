from dataclasses import dataclass


@dataclass
class Command:
    name: str
    text: str


class CommandManager:

    def build(self, intent):
        return Command(
            name=intent["name"],
            text=intent.get("text", "")
        )

    def execute(self, command, memory, android):

        if command.name == "help":
            return (
                "ALPHA OS COMMANDS\n"
                "-----------------\n"
                "remember project = Alpha OS\n"
                "recall project\n"
                "open https://example.com\n"
                "notify Hello Alpha\n"
                "status\n"
                "help\n"
                "exit"
            )

        if command.name == "status":
            return (
                "ALPHA OS ONLINE\n"
                f"Memory entries: {memory.count()}"
            )

        if command.name == "remember":

            text = command.text

            if "=" in text:
                key, value = text.split("=", 1)

            elif " is " in text.lower():
                key, value = text.split(" is ", 1)

            else:
                return (
                    "Isticmaal:\n"
                    "remember project = Alpha OS"
                )

            key = key.strip()
            value = value.strip()

            if not key or not value:
                return "Key iyo value waa loo baahan yahay."

            memory.remember(key, value)

            return f"Memory saved: {key}"

        if command.name == "recall":

            value = memory.recall(command.text)

            if value is None:
                return (
                    f"Memory lama helin: "
                    f"{command.text}"
                )

            return value

        if command.name == "open":

            url = command.text

            if not url.startswith(
                ("https://", "http://")
            ):
                return (
                    "Security: kaliya "
                    "http/https URLs ayaa la oggol yahay."
                )

            if android.open_url(url):
                return "URL-ka waa la furay."

            return (
                "Ma furin URL-ka. "
                "Hubi Termux:API."
            )

        if command.name == "notify":

            if android.notify(
                "ALPHA OS",
                command.text
            ):
                return "Notification waa la diray."

            return (
                "Notification lama dirin. "
                "Hubi Termux:API."
            )

        if command.name == "chat":

            return (
                "Alpha Core: "
                f"Waan helay → {command.text}"
            )

        if command.name == "exit":
            return "ALPHA OS shutting down..."

        return "Unknown command."
