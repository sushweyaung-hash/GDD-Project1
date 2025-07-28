from PIL import Image

TARGET_SIZE = (30, 20)  # 2x player.png size (15x10)

for fname in ["boss.png", "boss2.png"]:
    try:
        img = Image.open(fname)
        img = img.resize(TARGET_SIZE, Image.LANCZOS)
        img.save(fname)
        print(f"Resized {fname} to {TARGET_SIZE}")
    except Exception as e:
        print(f"Error resizing {fname}: {e}") 