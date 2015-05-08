package com.example.fishpos;

public class SpinnerFishType {
     
        private  String fishType = "";
        private  int imageFile = 0;
         
        /*********** Set Methods ******************/
        public void setFishType(String fishType)
        {
            this.fishType = fishType;
        }
         
        public void setImageFile(int imageFile)
        {
            this.imageFile = imageFile;
        }
         
        /*********** Get Methods ****************/
        public String getFishType()
        {
            return this.fishType;
        }
         
        public int getImageFile()
        {
            return this.imageFile;
        }
  }