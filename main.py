from alpha.core import AlphaCore


def main():

    alpha = AlphaCore()

    print()
    print("==============================")
    print("        ALPHA OS v1.1")
    print("==============================")
    print("AI Core: ONLINE")
    print("Memory: ONLINE")
    print("Security: ONLINE")
    print("Android Bridge: ONLINE")
    print("Type 'help' for commands.")
    print()

    while True:

        try:
            text = input("Alpha > ").strip()

        except (KeyboardInterrupt, EOFError):
            print("\nAlpha OS stopped.")
            break

        if not text:
            continue

        response = alpha.handle(text)

        print("Alpha:", response)

        if text.lower() in (
            "exit",
            "quit",
            "bye"
        ):
            break


if __name__ == "__main__":
    main()
