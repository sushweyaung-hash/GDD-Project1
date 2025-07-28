from PIL import Image

TARGET_SIZE = (40, 39)

for fname in ["powerup-m.png", "powerup-m2.png"]:
    try:
        img = Image.open(fname)
        img = img.resize(TARGET_SIZE, Image.LANCZOS)
        img.save(fname)
        print(f"Resized {fname} to {TARGET_SIZE}")
    except Exception as e:
        print(f"Error resizing {fname}: {e}") 