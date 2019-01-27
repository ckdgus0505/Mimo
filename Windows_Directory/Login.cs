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
            txtPasswd.KeyDown += TxtPasswd_KeyDown;
            btnOFL.Click += BtnOFL_Click;
        }

        private void BtnOFL_Click(object sender, EventArgs e)
        {
            Form1 F1 = new Form1(0);
            this.Visible = false;
            F1.ShowDialog();
            this.Close();
        }

        private void TxtPasswd_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnLogin.PerformClick();
            }
        }

        private void BtnReg_Click(object sender, EventArgs e)
        {
            if (txtId.Text != "" && txtPasswd.Text != "")
            {
                string insertQuery = "INSERT INTO MEMBER(ID, PASSWORD) VALUE( \""+txtId.Text.ToString()+ " \", \"" + txtPasswd.Text.ToString() + "\");";

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
            else
            {
                MessageBox.Show("ID 혹은 암호가 입력되지 않았습니다.");
            }

        }

        private void BtnLogin_Click(object sender, EventArgs e)
        {

            if (txtId.Text != "" && txtPasswd.Text != "")
            {
                string selectQuery = "SELECT ID, PASSWORD FROM MEMBER WHERE ID = \"" + txtId.Text.ToString() + "\" and PASSWORD = \"" + txtPasswd.Text.ToString() +" \";";
                string IDOKQuery = "SELECT ID, PASSWORD FROM MEMBER WHERE ID = \"" + txtId.Text.ToString() + "\";";
                MySqlCommand commend = new MySqlCommand(selectQuery, connection);
                connection.Open();
                
                MySqlDataReader reader = commend.ExecuteReader();

                int count = 0;
                int is_account = 0;
                while (reader.Read())
                {
                    count++;
                }
                reader.Close();
                commend.CommandText = IDOKQuery;
                MySqlDataReader reader2 = commend.ExecuteReader();
                while (reader2.Read())
                {
                    is_account++;
                }
                connection.Close();
                if (count == 1)
                {
                    ID = txtId.Text.ToString();
                    
                    //MessageBox.Show("로그인 되었습니다.");
                    Form1 F1 = new Form1(1, ID);
                    this.Visible = false;
                    F1.ShowDialog();
                    this.Close();
                }
                else if(is_account == 1 && count == 0)
                {
                    MessageBox.Show("비밀번호를 잘못 입력하셨습니다.");
                }
                else if (is_account == 0)
                {
                    MessageBox.Show("없는 계정입니다.");
                }
            }
            else
            {
                MessageBox.Show("ID 혹은 암호가 입력되지 않았습니다.");
            }
        }
    }
}
