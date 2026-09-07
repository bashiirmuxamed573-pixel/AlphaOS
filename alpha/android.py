import subprocess
import shutil


class AndroidBridge:

    def available(self, command):
        return shutil.which(command) is not None

    def run(self, command, *args, timeout=20):
        if not self.available(command):
            return False, f"{command} lama helin"

        try:
            result = subprocess.run(
                [command, *args],
                capture_output=True,
                text=True,
                timeout=timeout
            )

            return (
                result.returncode == 0,
                result.stdout.strip() or result.stderr.strip()
            )

        except Exception as e:
            return False, str(e)

    def text_to_speech(self, text):
        # Somali locale; actual voice depends on Android TTS engine.
        ok, output = self.run(
            "termux-tts-speak",
            "-l", "so",
            "-r", "0.9",
            "-p", "1.0",
            text
        )
        return ok, output

    def speech_to_text(self):
        ok, output = self.run(
            "termux-speech-to-text",
            timeout=30
        )

        if ok:
            return output

        return ""

    def notify(self, title, message):
        return self.run(
            "termux-notification",
            "--title", title,
            "--content", message
        )

    def open_url(self, url):
        if not url.startswith(("https://", "http://")):
            return False, "URL aan ammaan ahayn"

        return self.run(
            "termux-open-url",
            url
        )
