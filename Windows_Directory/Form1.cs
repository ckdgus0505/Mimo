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
using System.Net;
using System.Net.Sockets;


namespace Mimo
{

    public partial class Form1 : Form
    {
        Byte[] data = new Byte[1024]; // 송/수신 버퍼;
        Byte[] Null = new Byte[1024]; // 송/수신 버퍼;
        String result; // 수신한 데이터를 디코딩 하기위한 변수;
        int length; // 수신한 데이터 크기 저장하는 변수
        string dirPath = @".\..\..\..\mimms\";
        string filename;
        string ip = "localhost";
        string ID;
        int mode; // 0이면 오프라인, 1이면 온라인 모드임;;
        TcpClient client = new TcpClient();
        
        public Form1(int MODE) // 오프라인 모드일때의 생성자
        {

            InitializeComponent();
            mode = MODE;
            ID = null;
            MessageBox.Show("오프라인모드로 접속되었습니다");
            dirPath = @".\mimms\";
            this.Text = "MIMO       모드 : 오프라인";
        }

            public Form1(int MODE, String id) // 온라인 모드일때의 생성자
        {
            InitializeComponent();
            mode = MODE;
            ID = id;
            MessageBox.Show(ID + "로 로그인 되었습니다");
            this.Text = "MIMO       모드 : 온라인       ID : " + ID;

            mimLists.Items.Add("a.txt");

        }

        private Socket connection()
        {
            //1. 접속할 종단점(서버측 소켓)생성
            IPAddress ip = IPAddress.Parse("127.0.0.1");//인자값 : 서버측 IP
            IPEndPoint endPoint = new IPEndPoint(ip, 1154);//인자값 : IPAddress,포트번호

            //2. Tcp Socket 생성
            Socket sock = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            //3. 접속(전화걸기)
            sock.Connect(endPoint);
            data = Encoding.Default.GetBytes(ID);
            sock.Send(data);
            System.Threading.Thread.Sleep(10);
            data = Null;
            return sock;
        }

        private void BtnHelp_Click(object sender, EventArgs e)
        {
            if (mode == 0)
            {
                string filePath;
                filePath = string.Concat(dirPath, "\\what is Mimo.mimm");
                string text = System.IO.File.ReadAllText(filePath, Encoding.UTF8);
                showMemo.Text = text;
                fileTitle.Text = "what is Mimo";
                return;
            }
        }

        private void BtnConvert_Click(object sender, EventArgs e)
        {
            if (mode == 0)
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
        }

        private void BtnOpen_Click(object sender, EventArgs e)
        {
            if (mode == 0)
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
            else
            {
                try
                {
                    Socket sock;
                    sock = connection();
                    Byte[] data = Encoding.Default.GetBytes("");
                    sock.Send(data);

                    System.Threading.Thread.Sleep(10);

                    filename = mimLists.SelectedItem.ToString();
                    data = Encoding.Default.GetBytes(filename);
                    MessageBox.Show(data.ToString());
                    sock.Send(data);

                    client.Close();
                }
                catch (Exception err)
                {
                    MessageBox.Show(err.ToString());
                }
                finally
                {
                    client.Close();
                }

            }
        }

        private void BtnDelete_Click(object sender, EventArgs e)
        {
            if (mode == 0)
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
                                btnNew.PerformClick();
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
            else
            {
                if (MessageBox.Show("선택한 메모를 삭제하시겠습니까?", "메모삭제", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    try
                    {
                        Socket sock;
                        sock = connection();
                        data = Encoding.Default.GetBytes("d");
                        sock.Send(data);
                        System.Threading.Thread.Sleep(10);
                        data = Null;
                        filename = mimLists.SelectedItem.ToString();
                        data = Encoding.Default.GetBytes(filename);
                        sock.Send(data); // 삭제할 파일 이름보냄
                        data = Null;
                        length = sock.Receive(data, SocketFlags.None);
                        result = Encoding.Default.GetString(data, data.Length, length);
                        MessageBox.Show(result);
                        data = Null;
                    }
                    catch (Exception err)
                    {
                        MessageBox.Show(err.ToString());
                    }
                    finally
                    {
                        client.Close();
                    }
                }
            }
        }


        private void BtnSave_Click(object sender, EventArgs e)
        {
            if (mode == 0)
            {
                try
                {
                    if (mimLists.SelectedItem != null)
                    {
                        if (mimLists.SelectedItem.ToString() == fileTitle.ToString())
                        {
                            string title = mimLists.SelectedItem.ToString();
                            title = string.Concat(title, ".mimm");
                            title = string.Concat('\\', title);
                            string filePath = string.Concat(dirPath, title);

                            if (System.IO.File.Exists(filePath))
                            {
                                System.IO.File.WriteAllText(filePath, showMemo.Text.ToString());
                            }
                        }
                        else
                        {
                            string title = mimLists.SelectedItem.ToString();
                            title = string.Concat(title, ".mimm");
                            title = string.Concat('\\', title);
                            string filePath = string.Concat(dirPath, title);
                            System.IO.File.Delete(filePath);
                            mimLists.Items.Remove(mimLists.SelectedItem);
                            btnNew.PerformClick();

                        }
                    }
                    else
                    {
                        btnNew.PerformClick();
                    }
                }
                catch (System.NullReferenceException err)
                {

                    MessageBox.Show("파일을 선택하세요");
                }
            }
        }

        private void BtnNew_Click(object sender, EventArgs e)
        {
            if (mode == 0)
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
        }

        private void BtnSync_Click(object sender, EventArgs e)
        {
            if (mode == 0)
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
            else
            {
                try
                {
                    string temp;
                    int num;
                    Socket sock;
                    sock = connection();
                    data = Encoding.Default.GetBytes("c");
                    sock.Send(data);
                    System.Threading.Thread.Sleep(10);
                    data = Null;
                    length = sock.Receive(data, data.Length, SocketFlags.None);
                    num = int.Parse(Encoding.Default.GetString(data));

                    for(int i = 0; i < num; i++)
                    {
                        length = sock.Receive(data);
                        temp = Encoding.Default.GetString(data);
                        mimLists.Items.Add(temp);
                        data = Null;
                    }
                    
                }
                catch (Exception err)
                {
                    MessageBox.Show(err.ToString());
                }
                finally
                {
                    client.Close();
                }

            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            btnSync.PerformClick();
        }
    }
}
