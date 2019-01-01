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
        string dirPath = @".\..\..\..\mimms\";
        string filename;



        public Form1()
        {
            InitializeComponent();
        }

        private void BtnHelp_Click(object sender, EventArgs e)
        {
            string filePath;
            filePath = string.Concat(dirPath, "\\what is Mimo.mimm");
            string text = System.IO.File.ReadAllText(filePath, Encoding.UTF8);
            showMemo.Text = text;
            fileTitle.Text = "what is Mimo";
            return;
        }

        private void BtnConvert_Click(object sender, EventArgs e)
        {
            String txtFilePath = null;
            String txtFileName = null;
            OpenFileDialog pFileDlg = new OpenFileDialog();
            pFileDlg.InitialDirectory = "C:\\";
            if (pFileDlg.ShowDialog() == DialogResult.OK)
            {
                txtFilePath = pFileDlg.FileName;
                txtFileName = txtFilePath.Split('\\')[txtFilePath.Split('\\').Length - 1];
                try
                {
                    if (txtFileName.Split('.')[1] == "txt")
                    {
                        FileInfo file = new FileInfo(txtFilePath);
                        file.CopyTo(dirPath + txtFileName.Split('.')[0] + ".mimm");
                        MessageBox.Show("성공적으로 변환되었습니다");
                    }
                    else
                    {
                        MessageBox.Show(".txt 파일을 선택해주세요");

                    }
                }
                catch (System.IO.IOException err)
                {
                    MessageBox.Show("동일한 이름의 파일이이미 있습니다");
                }
            }

            btnSync.PerformClick();
        }

        private void BtnOpen_Click(object sender, EventArgs e)
        {
            string filePath, title;
            try
            {
                title = mimLists.SelectedItem.ToString();
                title = string.Concat(title, ".mimm");
                title = string.Concat('\\', title);
                filePath = string.Concat(dirPath, title);
                string text = System.IO.File.ReadAllText(filePath, Encoding.Unicode);
                showMemo.Text = text;
                fileTitle.Text = mimLists.SelectedItem.ToString();
            }
            catch (System.NullReferenceException err)
            {
                MessageBox.Show("파일을 선택하세요");
                return;
            }
            catch (Exception err)
            {
                MessageBox.Show(err.ToString());
                return;
            }



        }

        private void BtnDelete_Click(object sender, EventArgs e)
        {
            string title, filePath;
            try
            {
                title = mimLists.SelectedItem.ToString();
                title = string.Concat(title, ".mimm");
                title = string.Concat('\\', title);
                filePath = string.Concat(dirPath, title);
                if (MessageBox.Show("선택한 메모를 삭제하시겠습니까?", "메모삭제", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    if (System.IO.File.Exists(filePath))
                    {
                        try
                        {
                            System.IO.File.Delete(filePath);
                            mimLists.Items.Remove(mimLists.SelectedItem);
                            showMemo.ResetText();
                            fileTitle.ResetText();
                            MessageBox.Show("성공적으로 삭제되었습니다");
                        }
                        catch (System.IO.IOException err)
                        {
                            return;
                        }
                    }
                }
            }
            catch (System.NullReferenceException err)
            {
                MessageBox.Show("파일을 선택하세요");
                showMemo.ResetText();
                fileTitle.ResetText();
            }
        }


        private void BtnSave_Click(object sender, EventArgs e)
        {

            try
            {
                string title = mimLists.SelectedItem.ToString();
                title = string.Concat(title, ".mimm");
                title = string.Concat('\\', title);
                string filePath = string.Concat(dirPath, title);
                if (System.IO.File.Exists(filePath))
                {
                    System.IO.File.WriteAllText(filePath, showMemo.Text.ToString());
                }
                else
                {
                    MessageBox.Show("파일을 새로 만듭니다");
                    System.IO.File.WriteAllText(filePath, showMemo.Text.ToString());
                }
            }
            catch (System.NullReferenceException err)
            {
                MessageBox.Show("파일을 선택하세요");
            }
            btnSync.PerformClick();
        }

        private void BtnNew_Click(object sender, EventArgs e)
        {
            string title = fileTitle.Text;
            if (title != "")
            {
                title = string.Concat(title, ".mimm");
                title = string.Concat('\\', title);
                string filePath = string.Concat(dirPath, title);
                try
                {
                    if (File.Exists(filePath))
                    {
                        MessageBox.Show("이미 같은 이름의 파일이 있습니다.");
                    }
                    else
                    {
                        System.IO.File.WriteAllText(filePath, showMemo.Text.ToString());
                    }
                }
                catch (Exception err)
                {
                    MessageBox.Show(err.ToString());
                }
            }
            else
            {
                MessageBox.Show("파일명을 입력하세요");
            }
            btnSync.PerformClick();
        }

        private void BtnSync_Click(object sender, EventArgs e)
        {
            mimLists.Items.Clear();
            dirPath = @".\..\..\..\mimms\";
            if (System.IO.Directory.Exists(@dirPath))
            {
                System.IO.DirectoryInfo di = new System.IO.DirectoryInfo(@dirPath);
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

        private void Form1_Load(object sender, EventArgs e)
        {
            btnSync.PerformClick();
        }
    }
}
