from PIL import Image

alien_img = Image.open("alien.png")
zigzag_img = Image.open("alienzigzag.png")

# Resize zigzag to match alien.png size
zigzag_resized = zigzag_img.resize(alien_img.size, Image.ANTIALIAS)
zigzag_resized.save("alienzigzag.png")

print("alienzigzag.png resized to match alien.png!")