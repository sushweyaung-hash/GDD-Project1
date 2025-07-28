from PIL import Image, ImageDraw

img = Image.new("RGBA", (12, 24), (255, 0, 0, 255))  # Bright red, 12x24 pixels
draw = ImageDraw.Draw(img)
draw.rectangle([2, 2, 10, 22], fill=(255, 255, 0, 255))  # Yellow center
img.save("shot.png")
print("Created a visible shot.png!")