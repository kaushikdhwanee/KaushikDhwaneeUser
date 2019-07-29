
package in.tiqs.kaushikdhwaneeuser;

public class Moviee
    {
        public String getMain_cat_id() {
            return main_cat_id;
        }

        public void setMain_cat_id(String main_cat_id) {
            this.main_cat_id = main_cat_id;
        }

        //  String name;
       private String main_cat_id;
        private String category;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private String desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        String image;

        public void Movie(String category)
        {
            // this.name = name;
            this.category = category;
        }

        //  public String getName() {

        //return name;
        //  }

        //   public void setName(String name) {
        //      this.name = name;
        //  }
//
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
