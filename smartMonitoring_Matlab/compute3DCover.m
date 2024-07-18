function z = compute3DCover(x, y, z, L, R, data)
N = length(x);
[l, m, n] = meshgrid(0:data:L,0:data:L,0:data:L);  
[row, col] = size(l);
for i = 1:N
    D = sqrt((l-x(i)).^2+(m-y(i)).^2+(n-z(i)).^2);
    [m0, n0] = find(D <= R);    
    Ind = (m0-1).*col+n0;
    M(Ind) = 1;                          
end
scale = sum(M(1:end))/(row*col);         
z = scale;