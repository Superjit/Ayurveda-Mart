package com.spring.entity;



public class DetailName  {

	    private Long id;

	    private String detailName;

	    private Long categoryId;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDetailName() {
			return detailName;
		}

		public void setDetailName(String detailName) {
			this.detailName = detailName;
		}

		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}

		@Override
		public String toString() {
			return "DetailName [id=" + id + ", detailName=" + detailName + ", categoryId=" + categoryId + "]";
		}

    

   
  
    
}
