from .memory import Memory
from .understanding import Understanding
from .security import Security
from .commands import CommandManager
from .android import AndroidBridge


class AlphaCore:

    def __init__(self):

        self.memory = Memory()
        self.understanding = Understanding()
        self.security = Security()
        self.commands = CommandManager()
        self.android = AndroidBridge()

    def handle(self, text):

        intent = self.understanding.parse(text)

        command = self.commands.build(intent)

        if not self.security.allowed(command.name):
            return "SECURITY: Command blocked."

        return self.commands.execute(
            command,
            self.memory,
            self.android
        )

    def listen(self):
        return self.android.speech_to_text()

    def speak(self, text):
        return self.android.text_to_speech(text)
