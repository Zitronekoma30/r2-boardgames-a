from PIL import Image
import os

# Directory with your PNGs
input_dir = "./view"
output_dir = "./resized"

# Create the output directory if it doesn't exist
os.makedirs(output_dir, exist_ok=True)

# Loop through all files in the input directory
for file_name in os.listdir(input_dir):
    if file_name.endswith(".png"):
        img_path = os.path.join(input_dir, file_name)
        img = Image.open(img_path)
        resized_img = img.resize((100, 100))
        
        output_path = os.path.join(output_dir, file_name)
        resized_img.save(output_path)

print("Resizing complete!")
