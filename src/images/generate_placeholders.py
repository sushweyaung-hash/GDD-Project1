from PIL import Image, ImageDraw, ImageFont
import os

frames = {
    "player": 2,
    "alien1_": 2,
    "alienzigzag": 2,
    "boss": 2,
    "shot": 2,
    "explosion": 4,
    "powerup-s": 2,
    "powerup-m": 2,
}

size = (32, 32)
colors = [
    (255, 0, 0), (0, 255, 0), (0, 0, 255), (255, 255, 0),
    (255, 0, 255), (0, 255, 255), (128, 128, 128), (255, 128, 0)
]

def make_img(name, idx, color):
    img = Image.new("RGBA", size, color)
    d = ImageDraw.Draw(img)
    text = f"{name}{idx+1}"
    d.text((4, 10), text, fill=(0,0,0))
    img.save(f"{name}{idx+1}.png")

for i, (base, count) in enumerate(frames.items()):
    for idx in range(count):
        make_img(base, idx, colors[(i+idx)%len(colors)])

print("Placeholder images generated!")