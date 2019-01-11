using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data;
using MySql.Data.MySqlClient;

namespace Mimo
{

    public partial class Login : Form
    {
        MySqlConnection connection = new MySqlConnection("Server=192.168.0.110;Database=MIMO;Uid=MIMO;Pwd=mimo;");
        string ID;
        String Passwd;
        DataTable table = new DataTable();
        public Login()
        {
            InitializeComponent();
            btnLogin.Click += BtnLogin_Click;
            btnReg.Click += BtnReg_Click;
        }

        private void BtnReg_Click(object sender, EventArgs e)
        {
            string insertQuery = "INSERT INTO MEMBER(ID, PASSWORD) VALUE( \" "+txtId.Text.ToString()+ " \", \" " + txtPasswd.Text.ToString() + "\");";

            connection.Open();
            MySqlCommand command = new MySqlCommand(insertQuery, connection);

            try
            {
                if (command.ExecuteNonQuery() == 1)
                {
                    MessageBox.Show("성공적으로 생성되었습니다.");
                    connection.Close();
                    //btnLogin.PerformClick();
                }
                else
                {
                    
                }
            }
            catch (Exception err)
            {
                // MessageBox.Show("이미 존재하는 아이디입니다.");
                MessageBox.Show(err.Message);
                connection.Close();
            }

        }

        private void BtnLogin_Click(object sender, EventArgs e)
        {

            if (txtId.Text != "" && txtPasswd.Text != "")
            {
                connection.Open();
                string selectQuery = "SELECT ID, PASSWORD FROM MEMBER WHERE ID = \" " + txtId.Text.ToString() + "\";";
                MySqlCommand commend = new MySqlCommand(selectQuery, connection);

                MySqlDataReader reader = commend.ExecuteReader();

                if (reader.Read())
                {
                    if (reader["PASSWORD"].ToString() == txtPasswd.Text.ToString())
                    {
                        ID = txtId.ToString();
                        connection.Close();
                        MessageBox.Show("로그인 되었습니다.");
                        Form1 F1 = new Form1(ID);
                        F1.Show();
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show("암호가 올바르지 않습니다.");
                    }   
                }
                else
                {
                    MessageBox.Show("일치하는 계정이 없습니다.");
                }
                connection.Close();
            }
            else
            {
                MessageBox.Show("ID 혹은 암호가 입력되지 않았습니다.");
            }
        }
    }
}
