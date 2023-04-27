import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAuditoriaNota } from 'app/shared/model/auditoria-nota.model';
import { getEntities } from './auditoria-nota.reducer';

export const AuditoriaNota = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const auditoriaNotaList = useAppSelector(state => state.auditoriaNota.entities);
  const loading = useAppSelector(state => state.auditoriaNota.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="auditoria-nota-heading" data-cy="AuditoriaNotaHeading">
        <Translate contentKey="labArquiJHipsterApp.auditoriaNota.home.title">Auditoria Notas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.auditoriaNota.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/auditoria-nota/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.auditoriaNota.home.createLabel">Create new Auditoria Nota</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {auditoriaNotaList && auditoriaNotaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.auditoriaId">Auditoria Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.notaAnterior">Nota Anterior</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.notaNueva">Nota Nueva</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.fechaModificacion">Fecha Modificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.ip">Ip</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.usuarioPortal">Usuario Portal</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.profesor">Profesor</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.auditoriaNota.nota">Nota</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {auditoriaNotaList.map((auditoriaNota, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/auditoria-nota/${auditoriaNota.id}`} color="link" size="sm">
                      {auditoriaNota.id}
                    </Button>
                  </td>
                  <td>{auditoriaNota.auditoriaId}</td>
                  <td>{auditoriaNota.notaAnterior}</td>
                  <td>{auditoriaNota.notaNueva}</td>
                  <td>
                    {auditoriaNota.fechaModificacion ? (
                      <TextFormat type="date" value={auditoriaNota.fechaModificacion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{auditoriaNota.ip}</td>
                  <td>{auditoriaNota.usuarioPortal}</td>
                  <td>
                    {auditoriaNota.profesor ? <Link to={`/profesor/${auditoriaNota.profesor.id}`}>{auditoriaNota.profesor.id}</Link> : ''}
                  </td>
                  <td>{auditoriaNota.nota ? <Link to={`/nota/${auditoriaNota.nota.id}`}>{auditoriaNota.nota.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/auditoria-nota/${auditoriaNota.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/auditoria-nota/${auditoriaNota.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/auditoria-nota/${auditoriaNota.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="labArquiJHipsterApp.auditoriaNota.home.notFound">No Auditoria Notas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AuditoriaNota;
