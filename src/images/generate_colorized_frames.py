import os
from PIL import Image, ImageEnhance

def colorize(img, mode='yellow'):
    # Convert to RGB if not already
    img = img.convert('RGBA')
    r, g, b, a = img.split()
    if mode == 'yellow':
        # Boost red and green channels for yellow
        r = r.point(lambda i: min(255, int(i * 1.2)))
        g = g.point(lambda i: min(255, int(i * 1.2)))
        # Blue stays the same
        new_img = Image.merge('RGBA', (r, g, b, a))
    elif mode == 'red':
        # Boost red, reduce green/blue
        r = r.point(lambda i: min(255, int(i * 1.3)))
        g = g.point(lambda i: int(i * 0.7))
        b = b.point(lambda i: int(i * 0.7))
        new_img = Image.merge('RGBA', (r, g, b, a))
    else:
        new_img = img
    return new_img

# List of fallback images to animate
images = [
    'player.png',
    'alien.png',
    'alienzigzag.png',
    'boss.png',
    'shot.png',
    'bomb.png',
    'explosion.png',
    'powerup-s.png',
    'powerup-m.png',
]

for fname in images:
    if not os.path.exists(fname):
        print(f"{fname} not found, skipping.")
        continue
    img = Image.open(fname)
    # Save original as is (player.png, etc.)
    # Save colorized as player2.png, etc.
    outname = fname.replace('.png', '2.png')
    # Alternate yellow/red for variety
    mode = 'yellow' if images.index(fname) % 2 == 0 else 'red'
    colorized = colorize(img, mode=mode)
    colorized.save(outname)
    print(f"Generated {outname} ({mode} shift)")

print("All colorized frames generated!") 