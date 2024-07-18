function z = WSNcoverD(x, y, z, L, R, data)
%% 计算WSN的3D覆盖率
% input：
% x        球心横坐标
% y        球心纵坐标
% z        球心垂坐标
% L        区域边长
% R        感知半径
% data     离散粒度
% output:
% z        覆盖率
% 节点总个数
N = length(x);
% 离散化区域内的点，离散为三维坐标下的点
[l, m, n] = meshgrid(0:data:L,0:data:L,0:data:L);        
[row, col] = size(l);
for i = 1:N
    % 计算坐标点到球心的距离，使用欧氏距离
    D = sqrt((l-x(i)).^2+(m-y(i)).^2+(n-z(i)).^2); 
    % 检测出球覆盖点的坐标
    [m0, n0] = find(D <= R);       
    % 坐标与索引转化
    Ind = (m0-1).*col+n0;  
    % 改变覆盖状态
    M(Ind) = 1;                            
end
% 计算覆盖比例，从而得出覆盖率
scale = sum(M(1:end))/(row*col);           
z = scale;
