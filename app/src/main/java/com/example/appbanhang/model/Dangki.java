package com.example.appbanhang.model;

public class Dangki {

        private String name;
        private String email;
        private String taikhoan;
        private String matkhau;
        private String sdt;

        public Dangki() {
        }

        public Dangki(String name, String email, String taikhoan, String matkhau, String sdt) {
                this.name = name;
                this.email = email;
                this.taikhoan = taikhoan;
                this.matkhau = matkhau;
                this.sdt = sdt;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getTaikhoan() {
                return taikhoan;
        }

        public void setTaikhoan(String taikhoan) {
                this.taikhoan = taikhoan;
        }

        public String getMatkhau() {
                return matkhau;
        }

        public void setMatkhau(String matkhau) {
                this.matkhau = matkhau;
        }

        public String getSdt() {
                return sdt;
        }

        public void setSdt(String sdt) {
                this.sdt = sdt;
        }

        @Override
        public String toString(){
                return "Dangki{" +
                        "name=" + name +
                        "email=" + email +
                        ", taikhoan=" + taikhoan +
                        ", matkhau='" + matkhau + '\'' +
                        ", sdt='" + sdt + '\'' +
                        '}';
        }
}
