function z = computeCover(x, y, L, R, data)
N = length(x);
[m, n] = meshgrid(0:data:L);
[row, col] = size(m);
for i = 1:N
    D = sqrt((m-x(i)).^2+(n-y(i)).^2);
    [m0, n0] = find(D <= R);
    Ind = (m0-1).*col+n0;
    M(Ind) = 1;
end
scale = sum(M(1:end))/(row*col);
z = scale;