function z = WSNcoverD(x, y, z, L, R, data)
%% ����WSN��3D������
% input��
% x        ���ĺ�����
% y        ����������
% z        ���Ĵ�����
% L        ����߳�
% R        ��֪�뾶
% data     ��ɢ����
% output:
% z        ������
% �ڵ��ܸ���
N = length(x);
% ��ɢ�������ڵĵ㣬��ɢΪ��ά�����µĵ�
[l, m, n] = meshgrid(0:data:L,0:data:L,0:data:L);        
[row, col] = size(l);
for i = 1:N
    % ��������㵽���ĵľ��룬ʹ��ŷ�Ͼ���
    D = sqrt((l-x(i)).^2+(m-y(i)).^2+(n-z(i)).^2); 
    % �����򸲸ǵ������
    [m0, n0] = find(D <= R);       
    % ����������ת��
    Ind = (m0-1).*col+n0;  
    % �ı串��״̬
    M(Ind) = 1;                            
end
% ���㸲�Ǳ������Ӷ��ó�������
scale = sum(M(1:end))/(row*col);           
z = scale;
