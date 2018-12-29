using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.IO;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Mimo
{

    public partial class Form1 : Form
    {
        string dirPath = @"..\mimms";
        string filename;

        public Form1()
        {
            InitializeComponent();
            btnSync.Click += BtnSync_Click;
        }

        private void BtnSync_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog fbd = new FolderBrowserDialog();

            //if (fbd.ShowDialog() == DialogResult.OK)
            //    dirPath = fbd.SelectedPath;

            dirPath = @"..\mimms";
            MessageBox.Show(dirPath);
            if (System.IO.Directory.Exists(dirPath))
            {
                System.IO.DirectoryInfo di = new System.IO.DirectoryInfo(dirPath);
                foreach (var item in di.GetFiles())
                {
                    if (item.Name.Split('.')[1] == "mimm")
                    {
                        filename = item.Name.Split('.')[0];
                        mimLists.Items.Add(filename);
                    }
                }
            }
        }

        private void FileRead(params string[] UserInfo)
        {
            FileStream fs = new FileStream(@"../mimms/hello.mimm", FileMode.OpenOrCreate, FileAccess.Read);
            StreamReader st = new StreamReader(fs, System.Text.Encoding.UTF8);

            st.BaseStream.Seek(0, SeekOrigin.Begin);

            while (st.Peek() > -1)
            {
                string temp = st.ReadLine();
                //MessageBox.Show(aaa);
            }
            st.Close();
            fs.Close();
        }
        private void FileWrite(string str)
        {
            FileStream fs = new FileStream(@"../mimms/hello.mimm", FileMode.Append, FileAccess.Write);
            //FileMode중 append는 이어쓰기. 파일이 없으면 만든다.

            StreamWriter sw = new StreamWriter(fs, System.Text.Encoding.UTF8);
            sw.WriteLine(str);
            sw.Flush();
            sw.Close();
            fs.Close();
        }

        private void mimLists_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}
