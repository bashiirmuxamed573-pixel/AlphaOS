class Security:

    ALLOWED_COMMANDS = {
        "help",
        "status",
        "remember",
        "recall",
        "chat",
        "open",
        "notify",
        "exit",
    }

    def allowed(self, command):
        return command in self.ALLOWED_COMMANDS
